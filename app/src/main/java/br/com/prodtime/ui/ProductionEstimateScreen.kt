package br.com.prodtime.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import br.com.prodtime.domain.ProductionEstimateCalculator
import br.com.prodtime.domain.ProductionEstimateInput
import br.com.prodtime.domain.ProductionEstimateResult
import br.com.prodtime.domain.HolidayDefinition
import java.math.BigDecimal
import java.time.LocalDate

private data class EstimateErrors(
    val period: String? = null,
    val speed: String? = null,
    val tapes: String? = null,
    val hours: String? = null,
    val waste: String? = null,
) {
    val hasErrors: Boolean get() = listOf(period, speed, tapes, hours, waste).any { it != null }
}

@Composable
fun ProductionEstimateScreen(holidayDefinitions: List<HolidayDefinition>, onBack: () -> Unit) {
    val today = LocalDate.now()
    var startEpochDay by rememberSaveable { mutableStateOf(today.toEpochDay()) }
    var endEpochDay by rememberSaveable { mutableStateOf(today.toEpochDay()) }
    var includeStart by rememberSaveable { mutableStateOf(true) }
    var includeEnd by rememberSaveable { mutableStateOf(true) }
    var includeSaturdays by rememberSaveable { mutableStateOf(false) }
    var includeSundays by rememberSaveable { mutableStateOf(false) }
    var workOnHolidays by rememberSaveable { mutableStateOf(false) }
    var speed by rememberSaveable { mutableStateOf("28") }
    var tapes by rememberSaveable { mutableStateOf("1") }
    var hours by rememberSaveable { mutableStateOf("16") }
    var waste by rememberSaveable { mutableStateOf("3") }
    var errors by remember { mutableStateOf(EstimateErrors()) }
    var generalError by remember { mutableStateOf<String?>(null) }
    var result by remember { mutableStateOf<ProductionEstimateResult?>(null) }

    val startDate = LocalDate.ofEpochDay(startEpochDay)
    val endDate = LocalDate.ofEpochDay(endEpochDay)

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            TextButton(onClick = onBack) { Text("Voltar") }
            Text("Quanto consigo produzir?", style = MaterialTheme.typography.headlineSmall)
            Text(
                "Informe o período e as condições de produção.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            SectionTitle("PERÍODO")
            DateField("Data inicial", startDate) { startEpochDay = it.toEpochDay() }
            DateField("Data final", endDate) { endEpochDay = it.toEpochDay() }
            errors.period?.let { Text(it, color = MaterialTheme.colorScheme.error) }

            SectionTitle("CALENDÁRIO")
            PolicySwitch("Considerar data inicial", includeStart) { includeStart = it }
            PolicySwitch("Considerar data final", includeEnd) { includeEnd = it }
            PolicySwitch("Trabalhar aos sábados", includeSaturdays) { includeSaturdays = it }
            PolicySwitch("Trabalhar aos domingos", includeSundays) { includeSundays = it }
            PolicySwitch("Trabalhar em feriados", workOnHolidays) { workOnHolidays = it }
            SectionTitle("PRODUÇÃO")
            NumericField(speed, { speed = it }, "Velocidade", "cm/min", errors.speed)
            NumericField(tapes, { tapes = it }, "Quantidade de fitas", "fitas", errors.tapes, integer = true)
            NumericField(hours, { hours = it }, "Horas produtivas por dia", "h/dia", errors.hours)
            NumericField(
                waste,
                { waste = it },
                "Desperdício",
                "%",
                errors.waste,
                imeAction = ImeAction.Done,
            )

            generalError?.let { GeneralError(it) }
            Button(
                onClick = {
                    val parsedSpeed = parseDecimalInput(speed)
                    val parsedTapes = parsePositiveIntInput(tapes)
                    val parsedHours = parseDecimalInput(hours)
                    val parsedWaste = parseDecimalInput(waste)
                    errors = EstimateErrors(
                        period = if (endDate < startDate) "A data final não pode ser anterior à inicial." else null,
                        speed = positiveDecimalError(parsedSpeed, "Informe uma velocidade maior que zero."),
                        tapes = if (parsedTapes == null) "Informe uma quantidade inteira maior que zero." else null,
                        hours = positiveDecimalError(parsedHours, "Informe horas maiores que zero."),
                        waste = wasteError(parsedWaste),
                    )
                    generalError = null
                    result = null
                    if (!errors.hasErrors) {
                        try {
                            result = ProductionEstimateCalculator.calculate(
                                ProductionEstimateInput(
                                    startDate = startDate,
                                    endDate = endDate,
                                    includeStartDate = includeStart,
                                    includeEndDate = includeEnd,
                                    includeSaturdays = includeSaturdays,
                                    includeSundays = includeSundays,
                                    workOnHolidays = workOnHolidays,
                                    holidayDefinitions = holidayDefinitions,
                                    speedCmPerMinute = requireNotNull(parsedSpeed),
                                    tapeCount = requireNotNull(parsedTapes),
                                    productiveHoursPerDay = requireNotNull(parsedHours),
                                    wastePercent = requireNotNull(parsedWaste),
                                ),
                            )
                        } catch (error: IllegalArgumentException) {
                            generalError = error.message ?: "Revise os dados informados."
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp),
            ) { Text("Calcular produção") }

            result?.let { estimate ->
                EstimateResultCard(estimate, startDate, endDate)
            }
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun EstimateResultCard(result: ProductionEstimateResult, startDate: LocalDate, endDate: LocalDate) {
    val noProductiveDays = result.calendar.productiveDays == 0
    val message = if (noProductiveDays) "Não há dias produtivos no período informado." else null
    val calendarRows = calendarIncludedDays(result.calendar, result.resolvedHolidays)
    val rows = buildList {
        add("Dias produtivos" to result.calendar.productiveDays.toString())
        calendarRows.forEach { add(it.label to it.count.toString()) }
        result.capacity?.let { capacity ->
            add("Produção bruta" to formatMeters(capacity.grossProductionMeters))
            add("Desperdício" to formatMeters(capacity.wasteMeters))
        }
        add("Período" to "${formatDate(startDate)} a ${formatDate(endDate)}")
    }
    ResultCard(
        headline = "Produção estimada",
        primaryValue = formatMeters(result.netProductionMeters),
        message = message,
        copyText = resultCopyText("Produção estimada", formatMeters(result.netProductionMeters), rows, message = message),
    ) {
        rows.forEach { (label, value) -> SummaryRow(label, value) }
    }
}

private fun positiveDecimalError(value: BigDecimal?, message: String): String? =
    if (value == null || value <= BigDecimal.ZERO) message else null

private fun wasteError(value: BigDecimal?): String? = when {
    value == null -> "Informe um percentual válido."
    value < BigDecimal.ZERO -> "O desperdício não pode ser negativo."
    value >= BigDecimal.valueOf(100) -> "O desperdício deve ser menor que 100%."
    else -> null
}

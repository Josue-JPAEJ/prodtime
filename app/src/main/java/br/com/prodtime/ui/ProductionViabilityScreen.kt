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
import br.com.prodtime.domain.HolidayDefinition
import br.com.prodtime.domain.ProductionViabilityAdvisor
import br.com.prodtime.domain.ProductionViabilityInput
import br.com.prodtime.domain.ProductionViabilityResult
import java.math.BigDecimal
import java.time.LocalDate

private data class ViabilityErrors(
    val target: String? = null,
    val period: String? = null,
    val speed: String? = null,
    val tapes: String? = null,
    val hours: String? = null,
    val waste: String? = null,
) {
    val hasErrors get() = listOf(target, period, speed, tapes, hours, waste).any { it != null }
}

@Composable
fun ProductionViabilityScreen(holidayDefinitions: List<HolidayDefinition>, onBack: () -> Unit) {
    val today = LocalDate.now()
    var target by rememberSaveable { mutableStateOf("") }
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
    var errors by remember { mutableStateOf(ViabilityErrors()) }
    var generalError by remember { mutableStateOf<String?>(null) }
    var result by remember { mutableStateOf<ProductionViabilityResult?>(null) }
    val startDate = LocalDate.ofEpochDay(startEpochDay)
    val endDate = LocalDate.ofEpochDay(endEpochDay)

    Scaffold { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            TextButton(onClick = onBack) { Text("Voltar") }
            Text("Verificar uma meta", style = MaterialTheme.typography.headlineSmall)
            Text(
                "Compare a capacidade do período com a quantidade desejada.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            SectionTitle("META")
            NumericField(target, { updated ->
                    target = updated
                    errors = errors.copy(target = null)
                }, "Quantidade desejada", "m", errors.target, integer = true)

            SectionTitle("PERÍODO")
            DateField("Data inicial", startDate) { startEpochDay = it.toEpochDay() }
            DateField("Data final", endDate) { endEpochDay = it.toEpochDay() }
            PolicySwitch("Considerar data inicial", includeStart) { includeStart = it }
            PolicySwitch("Considerar data final", includeEnd) { includeEnd = it }
            errors.period?.let { Text(it, color = MaterialTheme.colorScheme.error) }

            SectionTitle("CALENDÁRIO")
            PolicySwitch("Trabalhar aos sábados", includeSaturdays) { includeSaturdays = it }
            PolicySwitch("Trabalhar aos domingos", includeSundays) { includeSundays = it }
            PolicySwitch("Trabalhar em feriados", workOnHolidays) { workOnHolidays = it }
            SectionTitle("PRODUÇÃO")
            NumericField(speed, { updated ->
                    speed = updated
                    errors = errors.copy(speed = null)
                }, "Velocidade", "cm/min", errors.speed)
            NumericField(tapes, { updated ->
                    tapes = updated
                    errors = errors.copy(tapes = null)
                }, "Quantidade atual de fitas", "fitas", errors.tapes, integer = true)
            NumericField(hours, { updated ->
                    hours = updated
                    errors = errors.copy(hours = null)
                }, "Horas produtivas por dia", "h/dia", errors.hours)
            NumericField(waste, { updated ->
                    waste = updated
                    errors = errors.copy(waste = null)
                }, "Desperdício", "%", errors.waste, imeAction = ImeAction.Done)

            generalError?.let { GeneralError(it) }
            Button(
                onClick = {
                    val parsedTarget = parseDecimalInput(target)
                    val parsedSpeed = parseDecimalInput(speed)
                    val parsedTapes = parsePositiveIntInput(tapes)
                    val parsedHours = parseDecimalInput(hours)
                    val parsedWaste = parseDecimalInput(waste)
                    errors = ViabilityErrors(
                        target = targetInputError(parsedTarget),
                        period = if (endDate < startDate) "A data final não pode ser anterior à inicial." else null,
                        speed = speedInputError(parsedSpeed),
                        tapes = tapeCountInputError(parsedTapes),
                        hours = productiveHoursInputError(parsedHours),
                        waste = wasteInputError(parsedWaste),
                    )
                    result = null
                    generalError = null
                    if (!errors.hasErrors) {
                        try {
                            result = ProductionViabilityAdvisor.evaluate(
                                ProductionViabilityInput(
                                    startDate, endDate, includeStart, includeEnd, includeSaturdays,
                                    includeSundays, workOnHolidays, holidayDefinitions,
                                    requireNotNull(parsedSpeed), requireNotNull(parsedTapes),
                                    requireNotNull(parsedHours), requireNotNull(parsedWaste),
                                    requireNotNull(parsedTarget),
                                ),
                            )
                        } catch (error: IllegalArgumentException) {
                            generalError = error.message ?: "Revise os dados informados."
                        } catch (_: IllegalStateException) {
                            generalError = "Não foi possível verificar a meta com as condições informadas."
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
            ) { Text("Verificar meta") }

            result?.let { ViabilityResultCard(it) }
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun ViabilityResultCard(result: ProductionViabilityResult) {
    val noProductiveDays = result.currentEstimate.calendar.productiveDays == 0
    val difference = presentDifference(result.differenceMeters)
    val headline = if (result.meetsTarget) "Meta atendida" else "Meta não atendida"
    val production = formatMeters(result.currentEstimate.netProductionMeters)
    val message = if (noProductiveDays) "Não há dias produtivos no período informado." else null
    val calendarRows = calendarIncludedDays(
        result.currentEstimate.calendar,
        result.currentEstimate.resolvedHolidays,
    )
    val rows = buildList {
        add("Dias produtivos" to result.currentEstimate.calendar.productiveDays.toString())
        calendarRows.forEach { add(it.label to it.count.toString()) }
        add("Meta" to formatMeters(result.targetMeters))
        add(difference.label to formatMeters(difference.meters).let { if (difference.label == "Excedente") "+$it" else it })
        add("Mínimo necessário" to (result.minimumTapeCount?.let { "$it fitas" } ?: "Não aplicável"))
        add("Fitas adicionais" to (result.additionalTapesNeeded?.toString() ?: "Não aplicável"))
    }
    ResultCard(
        headline = headline,
        primaryValue = production,
        primaryLabel = "Produção",
        message = message,
        containerColor = if (result.meetsTarget) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.errorContainer,
        contentColor = if (result.meetsTarget) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onErrorContainer,
        copyText = resultCopyText(headline, production, rows, primaryLabel = "Produção", message = message),
    ) {
        rows.forEach { (label, value) -> SummaryRow(label, value) }
    }
}

package br.com.prodtime.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import br.com.prodtime.domain.ProductionDeadlineCalculator
import br.com.prodtime.domain.ProductionDeadlineInput
import br.com.prodtime.domain.ProductionDeadlineResult
import java.math.BigDecimal
import java.time.LocalDate

private data class DeadlineErrors(
    val target: String? = null,
    val speed: String? = null,
    val tapes: String? = null,
    val hours: String? = null,
    val waste: String? = null,
) {
    val hasErrors: Boolean get() = listOf(target, speed, tapes, hours, waste).any { it != null }
}

private data class DeadlineDisplay(
    val result: ProductionDeadlineResult,
    val targetMeters: BigDecimal,
)

@Composable
fun ProductionDeadlineScreen(onBack: () -> Unit) {
    val today = LocalDate.now()
    var startEpochDay by rememberSaveable { mutableStateOf(today.toEpochDay()) }
    var includeStart by rememberSaveable { mutableStateOf(true) }
    var includeSaturdays by rememberSaveable { mutableStateOf(false) }
    var includeSundays by rememberSaveable { mutableStateOf(false) }
    var workOnHolidays by rememberSaveable { mutableStateOf(false) }
    var target by rememberSaveable { mutableStateOf("") }
    var speed by rememberSaveable { mutableStateOf("28") }
    var tapes by rememberSaveable { mutableStateOf("1") }
    var hours by rememberSaveable { mutableStateOf("16") }
    var waste by rememberSaveable { mutableStateOf("3") }
    var errors by remember { mutableStateOf(DeadlineErrors()) }
    var generalError by remember { mutableStateOf<String?>(null) }
    var display by remember { mutableStateOf<DeadlineDisplay?>(null) }
    val startDate = LocalDate.ofEpochDay(startEpochDay)

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            TextButton(onClick = onBack) { Text("‹ Voltar") }
            Text("Quando vou terminar?", style = MaterialTheme.typography.headlineSmall)
            Text(
                "Informe a quantidade desejada e as condições de produção.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            SectionTitle("META")
            NumericField(target, { target = it }, "Quantidade desejada", "m", errors.target, integer = true)

            SectionTitle("INÍCIO")
            DateField("Data inicial", startDate) { startEpochDay = it.toEpochDay() }
            PolicySwitch("Considerar data inicial", includeStart) { includeStart = it }

            SectionTitle("CALENDÁRIO")
            PolicySwitch("Trabalhar aos sábados", includeSaturdays) { includeSaturdays = it }
            PolicySwitch("Trabalhar aos domingos", includeSundays) { includeSundays = it }
            PolicySwitch("Trabalhar em feriados", workOnHolidays) { workOnHolidays = it }
            Text("Feriados cadastrados: 0", color = MaterialTheme.colorScheme.onSurfaceVariant)

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
                    val parsedTarget = parseDecimalInput(target)
                    val parsedSpeed = parseDecimalInput(speed)
                    val parsedTapes = parsePositiveIntInput(tapes)
                    val parsedHours = parseDecimalInput(hours)
                    val parsedWaste = parseDecimalInput(waste)
                    errors = DeadlineErrors(
                        target = targetError(parsedTarget),
                        speed = positiveDecimalErrorForDeadline(parsedSpeed, "Informe uma velocidade maior que zero."),
                        tapes = if (parsedTapes == null) "Informe uma quantidade inteira maior que zero." else null,
                        hours = positiveDecimalErrorForDeadline(parsedHours, "Informe horas maiores que zero."),
                        waste = wasteErrorForDeadline(parsedWaste),
                    )
                    generalError = null
                    display = null
                    if (!errors.hasErrors) {
                        try {
                            val deadline = ProductionDeadlineCalculator.calculate(
                                ProductionDeadlineInput(
                                    startDate = startDate,
                                    includeStartDate = includeStart,
                                    includeSaturdays = includeSaturdays,
                                    includeSundays = includeSundays,
                                    workOnHolidays = workOnHolidays,
                                    holidayDefinitions = emptyList(),
                                    speedCmPerMinute = requireNotNull(parsedSpeed),
                                    tapeCount = requireNotNull(parsedTapes),
                                    productiveHoursPerDay = requireNotNull(parsedHours),
                                    wastePercent = requireNotNull(parsedWaste),
                                    targetMeters = requireNotNull(parsedTarget),
                                ),
                            )
                            display = DeadlineDisplay(deadline, parsedTarget)
                        } catch (error: IllegalArgumentException) {
                            generalError = error.message ?: "Revise os dados informados."
                        } catch (_: IllegalStateException) {
                            generalError = "Não foi possível estimar a conclusão com as condições informadas."
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
            ) { Text("Calcular prazo") }

            display?.let { DeadlineResultCard(it) }
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun DeadlineResultCard(display: DeadlineDisplay) {
    val result = display.result
    val balance = requireNotNull(result.capacity.balanceMeters)
    val formattedBalance = when {
        balance > BigDecimal.ZERO -> "+${formatMeters(balance)}"
        else -> formatMeters(balance)
    }
    ResultCard(
        headline = "Conclusão estimada",
        primaryValue = formatDate(result.completionDate),
    ) {
        SummaryRow("Meta", formatMeters(display.targetMeters))
        SummaryRow("Dias produtivos", result.requiredProductiveDays.toString())
        SummaryRow("Produção estimada na conclusão", formatMeters(result.capacity.netProductionMeters))
        SummaryRow("Saldo", formattedBalance)
    }
}

private fun targetError(value: BigDecimal?): String? = when {
    value == null -> "Informe uma quantidade válida."
    value <= BigDecimal.ZERO -> "A quantidade deve ser maior que zero."
    value.stripTrailingZeros().scale() > 0 -> "A quantidade deve ser informada em metros inteiros."
    else -> null
}

private fun positiveDecimalErrorForDeadline(value: BigDecimal?, message: String): String? =
    if (value == null || value <= BigDecimal.ZERO) message else null

private fun wasteErrorForDeadline(value: BigDecimal?): String? = when {
    value == null -> "Informe um percentual válido."
    value < BigDecimal.ZERO -> "O desperdício não pode ser negativo."
    value >= BigDecimal.valueOf(100) -> "O desperdício deve ser menor que 100%."
    else -> null
}

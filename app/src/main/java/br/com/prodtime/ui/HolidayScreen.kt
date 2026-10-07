package br.com.prodtime.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import br.com.prodtime.domain.AnnualHoliday
import br.com.prodtime.domain.HolidayDefinition
import br.com.prodtime.domain.SpecificDateHoliday
import java.time.LocalDate
import java.time.MonthDay
import java.time.format.DateTimeFormatter

@Composable
fun HolidayScreen(
    holidayDefinitions: List<HolidayDefinition>,
    onAdd: (HolidayDefinition) -> Unit,
    onRemove: (HolidayDefinition) -> Unit,
    onBack: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var name by rememberSaveable { mutableStateOf("") }
    var annual by rememberSaveable { mutableStateOf(true) }
    var dateEpochDay by rememberSaveable { mutableStateOf(LocalDate.now().toEpochDay()) }
    var nameError by rememberSaveable { mutableStateOf<String?>(null) }
    val selectedDate = LocalDate.ofEpochDay(dateEpochDay)

    Scaffold { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            TextButton(onClick = onBack) { Text("Voltar") }
            Text("Feriados", style = MaterialTheme.typography.headlineSmall)
            Text(
                "Os feriados cadastrados ficam disponíveis durante esta sessão.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            SectionTitle("NOVO FERIADO")
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome") },
                supportingText = { nameError?.let { Text(it) } },
                isError = nameError != null,
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier.fillMaxWidth(),
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                if (annual) Button(
                    onClick = {},
                    modifier = Modifier.weight(1f).semantics { selected = true },
                ) { Text("Anual") }
                else OutlinedButton(
                    onClick = { annual = true },
                    modifier = Modifier.weight(1f).semantics { selected = false },
                ) { Text("Anual") }
                if (!annual) Button(
                    onClick = {},
                    modifier = Modifier.weight(1f).semantics { selected = true },
                ) { Text("Data específica") }
                else OutlinedButton(
                    onClick = { annual = false },
                    modifier = Modifier.weight(1f).semantics { selected = false },
                ) { Text("Data específica") }
            }
            DateField(
                label = if (annual) "Dia/mês" else "Data completa",
                date = selectedDate,
                displayValue = if (annual) {
                    { date -> date.format(DateTimeFormatter.ofPattern("dd/MM")) }
                } else {
                    ::formatDate
                },
            ) { dateEpochDay = it.toEpochDay() }
            Button(
                onClick = {
                    val normalizedName = name.trim()
                    nameError = if (normalizedName.isEmpty()) "Informe o nome do feriado." else null
                    if (nameError == null) {
                        onAdd(
                            if (annual) AnnualHoliday(normalizedName, MonthDay.from(selectedDate))
                            else SpecificDateHoliday(normalizedName, selectedDate),
                        )
                        name = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) { Text("Adicionar feriado") }

            SectionTitle("FERIADOS CADASTRADOS (${holidayDefinitions.size})")
            if (holidayDefinitions.isEmpty()) {
                Text("Nenhum feriado cadastrado.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            holidayDefinitions.forEach { holiday ->
                HolidayItem(holiday = holiday, onRemove = { onRemove(holiday) })
            }
        }
    }
}

@Composable
private fun HolidayItem(holiday: HolidayDefinition, onRemove: () -> Unit) {
    val description = when (holiday) {
        is AnnualHoliday -> "Anual • ${holiday.monthDay.format(DateTimeFormatter.ofPattern("dd/MM"))}"
        is SpecificDateHoliday -> "Data específica • ${formatDate(holiday.date)}"
    }
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(holiday.name, style = MaterialTheme.typography.titleMedium)
                Text(description, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            TextButton(onClick = onRemove) { Text("Remover") }
        }
    }
}

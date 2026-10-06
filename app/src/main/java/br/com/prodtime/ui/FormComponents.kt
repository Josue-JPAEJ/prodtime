@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package br.com.prodtime.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import br.com.prodtime.R

@Composable
internal fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 12.dp),
    )
    HorizontalDivider(modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))
}

@Composable
internal fun NumericField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    unit: String,
    error: String?,
    integer: Boolean = false,
    imeAction: ImeAction = ImeAction.Next,
) {
    val focusManager = LocalFocusManager.current
    var fieldValue by remember { mutableStateOf(TextFieldValue(value, TextRange(value.length))) }
    var wasFocused by remember { mutableStateOf(false) }

    LaunchedEffect(value) {
        if (value != fieldValue.text) {
            fieldValue = fieldValue.copy(
                text = value,
                selection = TextRange(
                    fieldValue.selection.start.coerceAtMost(value.length),
                    fieldValue.selection.end.coerceAtMost(value.length),
                ),
            )
        }
    }
    OutlinedTextField(
        value = fieldValue,
        onValueChange = {
            val sanitizedText = sanitizeNumericInput(it.text)
            val sanitizedSelectionStart = sanitizeNumericInput(it.text.substring(0, it.selection.start)).length
            val sanitizedSelectionEnd = sanitizeNumericInput(it.text.substring(0, it.selection.end)).length
            fieldValue = if (sanitizedText == it.text) {
                it
            } else {
                TextFieldValue(
                    text = sanitizedText,
                    selection = TextRange(sanitizedSelectionStart, sanitizedSelectionEnd),
                )
            }
            onValueChange(sanitizedText)
        },
        label = { Text(label) },
        supportingText = { Text(error ?: unit) },
        isError = error != null,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = if (integer) KeyboardType.Number else KeyboardType.Decimal,
            imeAction = imeAction,
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Next) },
            onDone = { focusManager.clearFocus() },
        ),
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                if (focusState.isFocused && !wasFocused) {
                    fieldValue = fieldValue.copy(selection = TextRange(fieldValue.text.length))
                }
                wasFocused = focusState.isFocused
            },
    )
}

@Composable
internal fun PolicySwitch(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {},
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(label, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
internal fun DateField(
    label: String,
    date: LocalDate,
    displayValue: (LocalDate) -> String = ::formatDate,
    onDateChange: (LocalDate) -> Unit,
) {
    var showPicker by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, style = MaterialTheme.typography.labelLarge)
        OutlinedButton(onClick = { showPicker = true }, modifier = Modifier.fillMaxWidth()) {
            Text(displayValue(date))
        }
    }

    if (showPicker) {
        val pickerState = rememberDatePickerState(
            initialSelectedDateMillis = date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli(),
        )
        DatePickerDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    pickerState.selectedDateMillis?.let { millis ->
                        onDateChange(Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate())
                    }
                    showPicker = false
                }) { Text("Confirmar") }
            },
            dismissButton = {
                TextButton(onClick = { showPicker = false }) { Text("Cancelar") }
            },
        ) {
            DatePicker(state = pickerState)
        }
    }
}

@Composable
internal fun SummaryRow(label: String, value: String) {
    val contentColor = LocalContentColor.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            label,
            modifier = Modifier.weight(1f),
            color = contentColor.copy(alpha = 0.75f),
        )
        Spacer(Modifier.width(16.dp))
        Text(
            value,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.End,
        )
    }
}

@Composable
internal fun ResultCard(
    headline: String,
    primaryValue: String,
    primaryLabel: String? = null,
    message: String? = null,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    copyText: String? = null,
    content: @Composable () -> Unit,
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = containerColor, contentColor = contentColor),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(headline, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                copyText?.let { text ->
                    IconButton(onClick = {
                        clipboardManager.setText(AnnotatedString(text))
                        Toast.makeText(context, "Resultado copiado.", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_content_copy),
                            contentDescription = "Copiar resultado",
                        )
                    }
                }
            }
            primaryLabel?.let {
                Text(it, color = LocalContentColor.current.copy(alpha = 0.75f))
            }
            Text(primaryValue, style = MaterialTheme.typography.headlineMedium)
            message?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
            content()
        }
    }
}

@Composable
internal fun GeneralError(message: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.onErrorContainer,
            modifier = Modifier.padding(16.dp),
        )
    }
}

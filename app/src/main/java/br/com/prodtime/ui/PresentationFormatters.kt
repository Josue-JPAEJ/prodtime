package br.com.prodtime.ui

import java.math.BigDecimal
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private val brazilianLocale = Locale.forLanguageTag("pt-BR")
private val brazilianDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", brazilianLocale)

fun parseDecimalInput(value: String): BigDecimal? = value
    .trim()
    .replace(',', '.')
    .takeIf(String::isNotEmpty)
    ?.let { normalized -> runCatching { BigDecimal(normalized) }.getOrNull() }

fun parsePositiveIntInput(value: String): Int? = value
    .trim()
    .takeIf { it.isNotEmpty() && it.all(Char::isDigit) }
    ?.toIntOrNull()
    ?.takeIf { it > 0 }

fun formatDate(date: LocalDate): String = date.format(brazilianDateFormatter)

fun formatMeters(value: BigDecimal): String {
    val normalized = value.stripTrailingZeros()
    val formatter = NumberFormat.getNumberInstance(brazilianLocale).apply {
        isGroupingUsed = true
        minimumFractionDigits = 0
        maximumFractionDigits = maxOf(normalized.scale(), 0)
    }
    return "${formatter.format(value)} m"
}

internal data class DifferencePresentation(val label: String, val meters: BigDecimal)

internal fun presentDifference(differenceMeters: BigDecimal): DifferencePresentation = when {
    differenceMeters > BigDecimal.ZERO -> DifferencePresentation("Excedente", differenceMeters)
    differenceMeters < BigDecimal.ZERO -> DifferencePresentation("Déficit", differenceMeters.abs())
    else -> DifferencePresentation("Diferença", BigDecimal.ZERO)
}

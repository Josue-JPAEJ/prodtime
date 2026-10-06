package br.com.prodtime.ui

import br.com.prodtime.domain.ProductiveCalendarResult
import java.time.DayOfWeek
import java.time.LocalDate

internal data class CalendarInclusionRow(val label: String, val count: Int)

internal fun calendarIncludedDays(
    calendar: ProductiveCalendarResult,
    resolvedHolidays: Set<LocalDate>,
): List<CalendarInclusionRow> {
    val productiveDates = calendar.productiveDates
    return listOf(
        inclusionRow("Feriado", "Feriados", productiveDates.count { it in resolvedHolidays }),
        inclusionRow("Sábado", "Sábados", productiveDates.count { it.dayOfWeek == DayOfWeek.SATURDAY }),
        inclusionRow("Domingo", "Domingos", productiveDates.count { it.dayOfWeek == DayOfWeek.SUNDAY }),
    ).filterNotNull()
}

private fun inclusionRow(singular: String, plural: String, count: Int): CalendarInclusionRow? =
    count.takeIf { it > 0 }?.let { CalendarInclusionRow(if (it == 1) singular else plural, it) }

internal fun resultCopyText(
    headline: String,
    primaryValue: String,
    rows: List<Pair<String, String>>,
    primaryLabel: String? = null,
    message: String? = null,
): String = buildList {
    add(headline)
    primaryLabel?.let { add("$it: $primaryValue") } ?: add(primaryValue)
    message?.let(::add)
    rows.forEach { (label, value) -> add("$label: $value") }
}.joinToString("\n")

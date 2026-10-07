package br.com.prodtime.domain

import java.time.DayOfWeek
import java.time.LocalDate

data class ProductiveCalendarInput(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val includeStartDate: Boolean,
    val includeEndDate: Boolean,
    val includeSaturdays: Boolean,
    val includeSundays: Boolean,
    val workOnHolidays: Boolean,
    val holidays: Set<LocalDate>,
)

data class ProductiveCalendarResult(
    val consideredDays: Int,
    val productiveDays: Int,
    val consideredDates: List<LocalDate>,
    val productiveDates: List<LocalDate>,
    val nonProductiveDates: List<LocalDate>,
    val saturdayCount: Int,
    val sundayCount: Int,
    val holidayCount: Int,
)

object ProductiveCalendarCalculator {
    fun calculate(input: ProductiveCalendarInput): ProductiveCalendarResult {
        require(input.startDate <= input.endDate) {
            "A data inicial deve ser anterior ou igual à data final."
        }

        val classifications = generateSequence(input.startDate) { date ->
            if (date < input.endDate) date.plusDays(1) else null
        }
            .filter { date ->
                (date != input.startDate || input.includeStartDate) &&
                    (date != input.endDate || input.includeEndDate)
            }
            .map { date -> classify(date, input) }
            .toList()

        val consideredDates = classifications.map(DateClassification::date)
        val productiveDates = classifications.filter(DateClassification::isProductive).map(DateClassification::date)
        val nonProductiveDates = classifications.filterNot(DateClassification::isProductive).map(DateClassification::date)

        return ProductiveCalendarResult(
            consideredDays = consideredDates.size,
            productiveDays = productiveDates.size,
            consideredDates = consideredDates,
            productiveDates = productiveDates,
            nonProductiveDates = nonProductiveDates,
            saturdayCount = classifications.count(DateClassification::isSaturday),
            sundayCount = classifications.count(DateClassification::isSunday),
            holidayCount = classifications.count(DateClassification::isHoliday),
        )
    }

    private fun classify(date: LocalDate, input: ProductiveCalendarInput): DateClassification {
        val isSaturday = date.dayOfWeek == DayOfWeek.SATURDAY
        val isSunday = date.dayOfWeek == DayOfWeek.SUNDAY
        val isHoliday = date in input.holidays
        val weekdayAllowsWork = when {
            isSaturday -> input.includeSaturdays
            isSunday -> input.includeSundays
            else -> true
        }
        val holidayAllowsWork = !isHoliday || input.workOnHolidays

        return DateClassification(
            date = date,
            isSaturday = isSaturday,
            isSunday = isSunday,
            isHoliday = isHoliday,
            isProductive = weekdayAllowsWork && holidayAllowsWork,
        )
    }

    private data class DateClassification(
        val date: LocalDate,
        val isSaturday: Boolean,
        val isSunday: Boolean,
        val isHoliday: Boolean,
        val isProductive: Boolean,
    )
}

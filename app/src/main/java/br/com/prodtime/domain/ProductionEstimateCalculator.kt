package br.com.prodtime.domain

import java.math.BigDecimal
import java.time.LocalDate

data class ProductionEstimateInput(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val includeStartDate: Boolean,
    val includeEndDate: Boolean,
    val includeSaturdays: Boolean,
    val includeSundays: Boolean,
    val workOnHolidays: Boolean,
    val holidayDefinitions: Collection<HolidayDefinition>,
    val speedCmPerMinute: BigDecimal,
    val tapeCount: Int,
    val productiveHoursPerDay: BigDecimal,
    val wastePercent: BigDecimal,
)

data class ProductionEstimateResult(
    val resolvedHolidays: Set<LocalDate>,
    val calendar: ProductiveCalendarResult,
    val capacity: ProductionCapacityResult?,
) {
    val netProductionMeters: BigDecimal
        get() = capacity?.netProductionMeters ?: BigDecimal.ZERO
}

object ProductionEstimateCalculator {
    fun calculate(input: ProductionEstimateInput): ProductionEstimateResult {
        val resolvedHolidays = HolidayResolver.resolve(
            definitions = input.holidayDefinitions,
            startDate = input.startDate,
            endDate = input.endDate,
        )
        val calendar = ProductiveCalendarCalculator.calculate(
            ProductiveCalendarInput(
                startDate = input.startDate,
                endDate = input.endDate,
                includeStartDate = input.includeStartDate,
                includeEndDate = input.includeEndDate,
                includeSaturdays = input.includeSaturdays,
                includeSundays = input.includeSundays,
                workOnHolidays = input.workOnHolidays,
                holidays = resolvedHolidays,
            ),
        )
        val capacity = if (calendar.productiveDays > 0) {
            ProductionCapacityCalculator.calculate(
                ProductionCapacityInput(
                    speedCmPerMinute = input.speedCmPerMinute,
                    tapeCount = input.tapeCount,
                    productiveHoursPerDay = input.productiveHoursPerDay,
                    productiveDays = calendar.productiveDays,
                    wastePercent = input.wastePercent,
                    targetMeters = null,
                ),
            )
        } else {
            null
        }

        return ProductionEstimateResult(
            resolvedHolidays = resolvedHolidays,
            calendar = calendar,
            capacity = capacity,
        )
    }
}

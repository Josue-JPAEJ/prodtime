package br.com.prodtime.domain

import java.math.BigDecimal
import java.time.LocalDate

data class ProductionDeadlineInput(
    val startDate: LocalDate,
    val includeStartDate: Boolean,
    val includeSaturdays: Boolean,
    val includeSundays: Boolean,
    val workOnHolidays: Boolean,
    val holidayDefinitions: Collection<HolidayDefinition>,
    val speedCmPerMinute: BigDecimal,
    val tapeCount: Int,
    val productiveHoursPerDay: BigDecimal,
    val wastePercent: BigDecimal,
    val targetMeters: BigDecimal,
)

data class ProductionDeadlineResult(
    val completionDate: LocalDate,
    val requiredProductiveDays: Int,
    val resolvedHolidays: Set<LocalDate>,
    val calendar: ProductiveCalendarResult,
    val capacity: ProductionCapacityResult,
)

object ProductionDeadlineCalculator {
    const val MAX_SEARCH_YEARS: Long = 100

    fun calculate(input: ProductionDeadlineInput): ProductionDeadlineResult {
        require(input.targetMeters > BigDecimal.ZERO) { "A meta em metros deve ser maior que zero." }
        require(input.targetMeters.stripTrailingZeros().scale() <= 0) {
            "A meta em metros deve ser um valor inteiro."
        }

        // Valida o contrato produtivo no motor que detém essas regras.
        capacityFor(input, productiveDays = 1)

        val searchEndDate = input.startDate.plusYears(MAX_SEARCH_YEARS)
        val searchHolidays = HolidayResolver.resolve(
            definitions = input.holidayDefinitions,
            startDate = input.startDate,
            endDate = searchEndDate,
        )
        var productiveDays = 0
        var candidate = input.startDate

        while (candidate <= searchEndDate) {
            val shouldConsider = candidate != input.startDate || input.includeStartDate
            if (shouldConsider && isProductive(candidate, input, searchHolidays)) {
                productiveDays++
                val capacity = capacityFor(input, productiveDays)
                if (requireNotNull(capacity.balanceMeters) >= BigDecimal.ZERO) {
                    return result(input, candidate, productiveDays, capacity)
                }
            }
            candidate = candidate.plusDays(1)
        }

        throw IllegalStateException("Não foi possível estimar a conclusão dentro do horizonte suportado.")
    }

    private fun isProductive(
        date: LocalDate,
        input: ProductionDeadlineInput,
        holidays: Set<LocalDate>,
    ): Boolean = ProductiveCalendarCalculator.calculate(
        ProductiveCalendarInput(
            startDate = date,
            endDate = date,
            includeStartDate = true,
            includeEndDate = true,
            includeSaturdays = input.includeSaturdays,
            includeSundays = input.includeSundays,
            workOnHolidays = input.workOnHolidays,
            holidays = holidays,
        ),
    ).productiveDays == 1

    private fun capacityFor(input: ProductionDeadlineInput, productiveDays: Int) =
        ProductionCapacityCalculator.calculate(
            ProductionCapacityInput(
                speedCmPerMinute = input.speedCmPerMinute,
                tapeCount = input.tapeCount,
                productiveHoursPerDay = input.productiveHoursPerDay,
                productiveDays = productiveDays,
                wastePercent = input.wastePercent,
                targetMeters = input.targetMeters,
            ),
        )

    private fun result(
        input: ProductionDeadlineInput,
        completionDate: LocalDate,
        productiveDays: Int,
        capacity: ProductionCapacityResult,
    ): ProductionDeadlineResult {
        val resolvedHolidays = HolidayResolver.resolve(
            definitions = input.holidayDefinitions,
            startDate = input.startDate,
            endDate = completionDate,
        )
        val calendar = ProductiveCalendarCalculator.calculate(
            ProductiveCalendarInput(
                startDate = input.startDate,
                endDate = completionDate,
                includeStartDate = input.includeStartDate,
                includeEndDate = true,
                includeSaturdays = input.includeSaturdays,
                includeSundays = input.includeSundays,
                workOnHolidays = input.workOnHolidays,
                holidays = resolvedHolidays,
            ),
        )
        check(calendar.productiveDays == productiveDays)

        return ProductionDeadlineResult(
            completionDate = completionDate,
            requiredProductiveDays = productiveDays,
            resolvedHolidays = resolvedHolidays,
            calendar = calendar,
            capacity = capacity,
        )
    }
}

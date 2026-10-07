package br.com.prodtime.domain

import java.math.BigDecimal
import java.time.LocalDate

data class ProductionViabilityInput(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val includeStartDate: Boolean,
    val includeEndDate: Boolean,
    val includeSaturdays: Boolean,
    val includeSundays: Boolean,
    val workOnHolidays: Boolean,
    val holidayDefinitions: Collection<HolidayDefinition>,
    val speedCmPerMinute: BigDecimal,
    val currentTapeCount: Int,
    val productiveHoursPerDay: BigDecimal,
    val wastePercent: BigDecimal,
    val targetMeters: BigDecimal,
)

data class ProductionViabilityResult(
    val currentEstimate: ProductionEstimateResult,
    val targetMeters: BigDecimal,
    val differenceMeters: BigDecimal,
    val meetsTarget: Boolean,
    val minimumTapeCount: Int?,
    val additionalTapesNeeded: Int?,
)

object ProductionViabilityAdvisor {
    private const val TECHNICAL_LIMIT_MESSAGE =
        "Não foi possível atingir a meta dentro do limite técnico suportado."

    fun evaluate(input: ProductionViabilityInput): ProductionViabilityResult {
        require(input.targetMeters > BigDecimal.ZERO) { "A meta em metros deve ser maior que zero." }
        require(input.targetMeters.stripTrailingZeros().scale() <= 0) {
            "A meta em metros deve ser um valor inteiro."
        }

        // O motor de estimativa não avalia a capacidade quando o calendário retorna zero.
        // Esta chamada mantém a validação dos parâmetros produtivos no motor que detém as regras.
        capacityFor(input, tapeCount = input.currentTapeCount, productiveDays = 1)

        val currentEstimate = ProductionEstimateCalculator.calculate(
            ProductionEstimateInput(
                startDate = input.startDate,
                endDate = input.endDate,
                includeStartDate = input.includeStartDate,
                includeEndDate = input.includeEndDate,
                includeSaturdays = input.includeSaturdays,
                includeSundays = input.includeSundays,
                workOnHolidays = input.workOnHolidays,
                holidayDefinitions = input.holidayDefinitions,
                speedCmPerMinute = input.speedCmPerMinute,
                tapeCount = input.currentTapeCount,
                productiveHoursPerDay = input.productiveHoursPerDay,
                wastePercent = input.wastePercent,
            ),
        )
        val differenceMeters = currentEstimate.netProductionMeters.subtract(input.targetMeters)
        val meetsTarget = differenceMeters >= BigDecimal.ZERO
        val productiveDays = currentEstimate.calendar.productiveDays

        if (productiveDays == 0) {
            return ProductionViabilityResult(
                currentEstimate = currentEstimate,
                targetMeters = input.targetMeters,
                differenceMeters = differenceMeters,
                meetsTarget = false,
                minimumTapeCount = null,
                additionalTapesNeeded = null,
            )
        }

        val minimumTapeCount = findMinimumTapeCount(input, productiveDays)
        return ProductionViabilityResult(
            currentEstimate = currentEstimate,
            targetMeters = input.targetMeters,
            differenceMeters = differenceMeters,
            meetsTarget = meetsTarget,
            minimumTapeCount = minimumTapeCount,
            additionalTapesNeeded = maxOf(minimumTapeCount - input.currentTapeCount, 0),
        )
    }

    private fun findMinimumTapeCount(input: ProductionViabilityInput, productiveDays: Int): Int {
        var lastInsufficient = 0
        var candidate = 1

        while (!meetsTarget(input, candidate, productiveDays)) {
            if (candidate == Int.MAX_VALUE) throw IllegalStateException(TECHNICAL_LIMIT_MESSAGE)
            lastInsufficient = candidate
            candidate = if (candidate > Int.MAX_VALUE / 2) Int.MAX_VALUE else candidate * 2
        }

        var lower = lastInsufficient + 1
        var upper = candidate
        while (lower < upper) {
            val middle = lower + (upper - lower) / 2
            if (meetsTarget(input, middle, productiveDays)) {
                upper = middle
            } else {
                lower = middle + 1
            }
        }
        return lower
    }

    private fun meetsTarget(input: ProductionViabilityInput, tapeCount: Int, productiveDays: Int): Boolean =
        capacityFor(input, tapeCount, productiveDays).netProductionMeters >= input.targetMeters

    private fun capacityFor(input: ProductionViabilityInput, tapeCount: Int, productiveDays: Int) =
        ProductionCapacityCalculator.calculate(
            ProductionCapacityInput(
                speedCmPerMinute = input.speedCmPerMinute,
                tapeCount = tapeCount,
                productiveHoursPerDay = input.productiveHoursPerDay,
                productiveDays = productiveDays,
                wastePercent = input.wastePercent,
                targetMeters = null,
            ),
        )
}

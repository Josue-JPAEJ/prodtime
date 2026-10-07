package br.com.prodtime.domain

import java.math.BigDecimal
import java.math.RoundingMode

data class ProductionCapacityInput(
    val speedCmPerMinute: BigDecimal,
    val tapeCount: Int,
    val productiveHoursPerDay: BigDecimal,
    val productiveDays: Int,
    val wastePercent: BigDecimal,
    val targetMeters: BigDecimal? = null,
)

data class ProductionCapacityResult(
    val metersPerHourPerTape: BigDecimal,
    val grossProductionMeters: BigDecimal,
    val wasteMeters: BigDecimal,
    val netProductionMeters: BigDecimal,
    val balanceMeters: BigDecimal?,
)

object ProductionCapacityCalculator {
    private val zero = BigDecimal.ZERO
    private val minutesPerHour = BigDecimal.valueOf(60)
    private val centimetersPerMeter = BigDecimal.valueOf(100)
    private val percentageBase = BigDecimal.valueOf(100)

    fun calculate(input: ProductionCapacityInput): ProductionCapacityResult {
        require(input.speedCmPerMinute > zero) { "A velocidade em cm/min deve ser maior que zero." }
        require(input.tapeCount > 0) { "A quantidade de fitas deve ser maior que zero." }
        require(input.productiveHoursPerDay > zero) { "As horas produtivas por dia devem ser maiores que zero." }
        require(input.productiveDays > 0) { "Os dias produtivos devem ser maiores que zero." }
        require(input.wastePercent >= zero) { "O percentual de desperdício não pode ser negativo." }
        require(input.wastePercent < percentageBase) { "O percentual de desperdício deve ser menor que 100." }
        require(input.targetMeters == null || input.targetMeters >= zero) { "A meta em metros não pode ser negativa." }
        require(input.targetMeters == null || input.targetMeters.stripTrailingZeros().scale() <= 0) {
            "A meta em metros deve ser um valor inteiro."
        }

        val metersPerHourPerTape = input.speedCmPerMinute
            .multiply(minutesPerHour)
            .divide(centimetersPerMeter)
        val grossProductionDecimal = metersPerHourPerTape
            .multiply(BigDecimal.valueOf(input.tapeCount.toLong()))
            .multiply(input.productiveHoursPerDay)
            .multiply(BigDecimal.valueOf(input.productiveDays.toLong()))
        val grossProductionMeters = grossProductionDecimal.setScale(0, RoundingMode.HALF_EVEN)
        val wasteMeters = grossProductionMeters
            .multiply(input.wastePercent)
            .divide(percentageBase)
        val netProductionMeters = grossProductionMeters
            .subtract(wasteMeters)
            .setScale(0, RoundingMode.HALF_EVEN)
        val balanceMeters = input.targetMeters?.let { targetMeters ->
            netProductionMeters.subtract(targetMeters)
        }

        return ProductionCapacityResult(
            metersPerHourPerTape = metersPerHourPerTape,
            grossProductionMeters = grossProductionMeters,
            wasteMeters = wasteMeters,
            netProductionMeters = netProductionMeters,
            balanceMeters = balanceMeters,
        )
    }
}

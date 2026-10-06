package br.com.prodtime.ui

import java.math.BigDecimal

internal val MAX_TARGET_METERS_EXCLUSIVE = BigDecimal("1000000000")
internal const val MAX_TAPE_COUNT_EXCLUSIVE = 1_000_000
internal val MAX_PRODUCTIVE_HOURS_PER_DAY = BigDecimal("24")
internal val MAX_SPEED_CM_PER_MINUTE_EXCLUSIVE = BigDecimal("1000")
private val MAX_WASTE_PERCENT_EXCLUSIVE = BigDecimal("100")

internal fun targetInputError(value: BigDecimal?): String? = when {
    value == null -> "Informe uma quantidade válida."
    value <= BigDecimal.ZERO -> "A quantidade deve ser maior que zero."
    value >= MAX_TARGET_METERS_EXCLUSIVE -> "A quantidade deve ser menor que 1.000.000.000 m."
    value.stripTrailingZeros().scale() > 0 -> "A quantidade deve ser informada em metros inteiros."
    else -> null
}

internal fun speedInputError(value: BigDecimal?): String? = when {
    value == null || value <= BigDecimal.ZERO -> "Informe uma velocidade maior que zero."
    value >= MAX_SPEED_CM_PER_MINUTE_EXCLUSIVE -> "A velocidade deve ser menor que 1.000 cm/min."
    else -> null
}

internal fun tapeCountInputError(value: Int?): String? = when {
    value == null -> "Informe uma quantidade inteira maior que zero."
    value >= MAX_TAPE_COUNT_EXCLUSIVE -> "A quantidade de fitas deve ser menor que 1.000.000."
    else -> null
}

internal fun productiveHoursInputError(value: BigDecimal?): String? = when {
    value == null || value <= BigDecimal.ZERO -> "Informe horas maiores que zero."
    value > MAX_PRODUCTIVE_HOURS_PER_DAY -> "As horas produtivas por dia não podem ultrapassar 24."
    else -> null
}

internal fun wasteInputError(value: BigDecimal?): String? = when {
    value == null -> "Informe um percentual válido."
    value < BigDecimal.ZERO -> "O desperdício não pode ser negativo."
    value >= MAX_WASTE_PERCENT_EXCLUSIVE -> "O desperdício deve ser menor que 100%."
    else -> null
}

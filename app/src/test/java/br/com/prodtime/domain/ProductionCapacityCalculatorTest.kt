package br.com.prodtime.domain

import java.math.BigDecimal
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class ProductionCapacityCalculatorTest {
    @Test
    fun `reproduz regressao historica com tres fitas`() {
        val result = calculate(tapeCount = 3)

        assertDecimalEquals("15", result.metersPerHourPerTape)
        assertDecimalEquals("13680", result.grossProductionMeters)
        assertDecimalEquals("410.4", result.wasteMeters)
        assertDecimalEquals("13270", result.netProductionMeters)
        assertDecimalEquals("3270", result.balanceMeters)
    }

    @Test
    fun `reproduz regressao historica com duas fitas`() {
        val result = calculate(tapeCount = 2)

        assertDecimalEquals("15", result.metersPerHourPerTape)
        assertDecimalEquals("9120", result.grossProductionMeters)
        assertDecimalEquals("273.6", result.wasteMeters)
        assertDecimalEquals("8846", result.netProductionMeters)
        assertDecimalEquals("-1154", result.balanceMeters)
    }

    @Test
    fun `half even arredonda empate para inteiro par inferior`() {
        val result = calculate(speed = "5", tapeCount = 1, hours = "1.5", days = 1, waste = "0", target = null)

        assertDecimalEquals("4", result.grossProductionMeters)
    }

    @Test
    fun `half even arredonda empate para inteiro par superior`() {
        val result = calculate(speed = "25", tapeCount = 1, hours = "0.5", days = 1, waste = "0", target = null)

        assertDecimalEquals("8", result.grossProductionMeters)
    }

    @Test
    fun `sem desperdicio mantem producao liquida igual a bruta`() {
        val result = calculate(waste = "0")

        assertTrue(result.grossProductionMeters.compareTo(result.netProductionMeters) == 0)
    }

    @Test
    fun `meta ausente produz saldo ausente`() {
        val result = calculate(target = null)

        assertNull(result.balanceMeters)
    }

    @Test
    fun `aceita horas produtivas fracionarias`() {
        val result = calculate(speed = "20", tapeCount = 2, hours = "7.5", days = 2, waste = "10", target = null)

        assertDecimalEquals("12", result.metersPerHourPerTape)
        assertDecimalEquals("360", result.grossProductionMeters)
        assertDecimalEquals("36", result.wasteMeters)
        assertDecimalEquals("324", result.netProductionMeters)
    }

    @Test
    fun `rejeita entradas fora do contrato`() {
        assertInvalid { calculate(speed = "0") }
        assertInvalid { calculate(tapeCount = 0) }
        assertInvalid { calculate(hours = "0") }
        assertInvalid { calculate(days = 0) }
        assertInvalid { calculate(waste = "-0.1") }
        assertInvalid { calculate(waste = "100") }
        assertInvalid { calculate(waste = "100.1") }
        assertInvalid { calculate(target = decimal("-1")) }
        assertInvalid { calculate(target = decimal("1.5")) }
    }

    private fun calculate(
        speed: String = "25",
        tapeCount: Int = 3,
        hours: String = "16",
        days: Int = 19,
        waste: String = "3",
        target: BigDecimal? = decimal("10000"),
    ) = ProductionCapacityCalculator.calculate(
        ProductionCapacityInput(
            speedCmPerMinute = decimal(speed),
            tapeCount = tapeCount,
            productiveHoursPerDay = decimal(hours),
            productiveDays = days,
            wastePercent = decimal(waste),
            targetMeters = target,
        ),
    )

    private fun assertInvalid(block: () -> Unit) {
        assertThrows(IllegalArgumentException::class.java) { block() }
    }

    private fun assertDecimalEquals(expected: String, actual: BigDecimal?) {
        requireNotNull(actual)
        assertTrue("Esperado $expected, obtido $actual", decimal(expected).compareTo(actual) == 0)
    }

    private fun decimal(value: String) = BigDecimal(value)
}

package br.com.prodtime.domain

import java.math.BigDecimal
import java.time.LocalDate
import java.time.MonthDay
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class ProductionEstimateCalculatorTest {
    @Test
    fun `E1 regressao de tres fitas usa os 19 dias calculados`() {
        val result = calculate()

        assertEquals(19, result.calendar.productiveDays)
        assertCapacity(result, gross = "13680", waste = "410.4", net = "13270")
    }

    @Test
    fun `E2 regressao de duas fitas usa os 19 dias calculados`() {
        val result = calculate(tapeCount = 2)

        assertEquals(19, result.calendar.productiveDays)
        assertCapacity(result, gross = "9120", waste = "273.6", net = "8846")
    }

    @Test
    fun `E3 feriado nao trabalhado reduz capacidade para 18 dias`() {
        val result = calculate(holidays = listOf(AnnualHoliday("Feriado", MonthDay.of(10, 5))))

        assertEquals(18, result.calendar.productiveDays)
        assertCapacity(result, gross = "12960", waste = "388.8", net = "12571")
    }

    @Test
    fun `E4 feriado trabalhado preserva capacidade de 19 dias`() {
        val result = calculate(
            holidays = listOf(AnnualHoliday("Feriado", MonthDay.of(10, 5))),
            workOnHolidays = true,
        )

        assertEquals(19, result.calendar.productiveDays)
        assertCapacity(result, gross = "13680", waste = "410.4", net = "13270")
    }

    @Test
    fun `E5 domingo unico sem trabalho retorna producao zero sem capacidade`() {
        val sunday = date(2026, 10, 4)
        val result = calculate(startDate = sunday, endDate = sunday)

        assertEquals(0, result.calendar.productiveDays)
        assertNull(result.capacity)
        assertDecimalEquals("0", result.netProductionMeters)
    }

    @Test
    fun `E6 exclusao da ponta inicial reduz um dia e altera capacidade`() {
        val included = calculate()
        val excluded = calculate(includeStartDate = false)

        assertEquals(1, included.calendar.productiveDays - excluded.calendar.productiveDays)
        assertTrue(included.netProductionMeters > excluded.netProductionMeters)
    }

    @Test
    fun `E7 feriado anual e resolvido no ano correto`() {
        val christmas = date(2026, 12, 25)
        val result = calculate(
            startDate = date(2026, 12, 24),
            endDate = date(2026, 12, 28),
            holidays = listOf(AnnualHoliday("Natal", MonthDay.of(12, 25))),
        )

        assertTrue(christmas in result.resolvedHolidays)
    }

    @Test
    fun `E8 feriado especifico e resolvido e classificado como nao produtivo`() {
        val holiday = date(2026, 10, 6)
        val result = calculate(holidays = listOf(SpecificDateHoliday("Evento", holiday)))

        assertTrue(holiday in result.resolvedHolidays)
        assertTrue(holiday in result.calendar.nonProductiveDates)
    }

    @Test
    fun `E9 definicoes coincidentes geram um feriado e uma classificacao`() {
        val holiday = date(2026, 10, 5)
        val result = calculate(
            holidays = listOf(
                AnnualHoliday("Anual", MonthDay.of(10, 5)),
                SpecificDateHoliday("Especifico", holiday),
            ),
        )

        assertEquals(setOf(holiday), result.resolvedHolidays)
        assertEquals(1, result.calendar.holidayCount)
    }

    @Test
    fun `E10 intervalo invertido e rejeitado`() {
        assertThrows(IllegalArgumentException::class.java) {
            calculate(startDate = END_DATE, endDate = START_DATE)
        }
    }

    @Test
    fun `E11 horas fracionarias atravessam integracao com BigDecimal`() {
        val monday = date(2026, 10, 5)
        val result = calculate(
            startDate = monday,
            endDate = monday,
            speed = "20",
            tapeCount = 2,
            hours = "7.5",
            waste = "10",
        )

        assertCapacity(result, gross = "180", waste = "18", net = "162")
    }

    @Test
    fun `E12 resultado mantem rastreabilidade coerente de todo o pipeline`() {
        val holiday = date(2026, 10, 5)
        val result = calculate(holidays = listOf(SpecificDateHoliday("Evento", holiday)))

        assertEquals(setOf(holiday), result.resolvedHolidays)
        assertEquals(result.calendar.consideredDays, result.calendar.consideredDates.size)
        assertEquals(result.calendar.productiveDays, result.calendar.productiveDates.size)
        assertEquals(
            result.calendar.consideredDates,
            (result.calendar.productiveDates + result.calendar.nonProductiveDates).sorted(),
        )
        assertEquals(18, result.calendar.productiveDays)
        requireNotNull(result.capacity)
    }

    private fun calculate(
        startDate: LocalDate = START_DATE,
        endDate: LocalDate = END_DATE,
        includeStartDate: Boolean = true,
        workOnHolidays: Boolean = false,
        holidays: Collection<HolidayDefinition> = emptyList(),
        speed: String = "25",
        tapeCount: Int = 3,
        hours: String = "16",
        waste: String = "3",
    ) = ProductionEstimateCalculator.calculate(
        ProductionEstimateInput(
            startDate = startDate,
            endDate = endDate,
            includeStartDate = includeStartDate,
            includeEndDate = true,
            includeSaturdays = false,
            includeSundays = false,
            workOnHolidays = workOnHolidays,
            holidayDefinitions = holidays,
            speedCmPerMinute = decimal(speed),
            tapeCount = tapeCount,
            productiveHoursPerDay = decimal(hours),
            wastePercent = decimal(waste),
        ),
    )

    private fun assertCapacity(result: ProductionEstimateResult, gross: String, waste: String, net: String) {
        val capacity = requireNotNull(result.capacity)
        assertDecimalEquals(gross, capacity.grossProductionMeters)
        assertDecimalEquals(waste, capacity.wasteMeters)
        assertDecimalEquals(net, capacity.netProductionMeters)
        assertNull(capacity.balanceMeters)
    }

    private fun assertDecimalEquals(expected: String, actual: BigDecimal) {
        assertTrue("Esperado $expected, obtido $actual", decimal(expected).compareTo(actual) == 0)
    }

    private fun decimal(value: String) = BigDecimal(value)

    private fun date(year: Int, month: Int, day: Int): LocalDate = LocalDate.of(year, month, day)

    private companion object {
        val START_DATE: LocalDate = LocalDate.of(2026, 10, 1)
        val END_DATE: LocalDate = LocalDate.of(2026, 10, 27)
    }
}

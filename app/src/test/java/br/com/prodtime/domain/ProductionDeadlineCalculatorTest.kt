package br.com.prodtime.domain

import java.math.BigDecimal
import java.time.LocalDate
import java.time.MonthDay
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class ProductionDeadlineCalculatorTest {
    @Test
    fun `D1 meta de dez mil metros com tres fitas`() {
        val result = calculate()

        assertEquals(15, result.requiredProductiveDays)
        assertEquals(date(2026, 10, 23), result.completionDate)
        assertDecimal("10476", result.capacity.netProductionMeters)
        assertDecimal("476", requireNotNull(result.capacity.balanceMeters))
    }

    @Test
    fun `D2 conclusao usa a primeira data que atinge a meta`() {
        val before = capacity(productiveDays = 14, target = "10000")
        val completion = capacity(productiveDays = 15, target = "10000")

        assertTrue(requireNotNull(before.balanceMeters) < BigDecimal.ZERO)
        assertTrue(requireNotNull(completion.balanceMeters) >= BigDecimal.ZERO)
        assertEquals(date(2026, 10, 23), calculate().completionDate)
    }

    @Test
    fun `D3 meta exata termina com saldo zero`() {
        val result = calculate(target = "10476")

        assertEquals(date(2026, 10, 23), result.completionDate)
        assertDecimal("0", requireNotNull(result.capacity.balanceMeters))
    }

    @Test
    fun `D4 um metro acima avanca ao proximo dia produtivo`() {
        assertEquals(date(2026, 10, 26), calculate(target = "10477").completionDate)
    }

    @Test
    fun `D5 feriado nao trabalhado atrasa prazo`() {
        val result = calculate(holidays = listOf(AnnualHoliday("Feriado", MonthDay.of(10, 12))))

        assertEquals(15, result.requiredProductiveDays)
        assertEquals(date(2026, 10, 26), result.completionDate)
    }

    @Test
    fun `D6 feriado trabalhado nao atrasa prazo`() {
        val holiday = AnnualHoliday("Feriado", MonthDay.of(10, 12))

        assertEquals(date(2026, 10, 23), calculate(holidays = listOf(holiday), workOnHolidays = true).completionDate)
    }

    @Test
    fun `D7 data inicial excluida desloca conclusao`() {
        val result = calculate(includeStartDate = false)

        assertEquals(15, result.requiredProductiveDays)
        assertEquals(date(2026, 10, 26), result.completionDate)
    }

    @Test
    fun `D8 inicio em domingo conclui no primeiro dia util`() {
        val oneDayNet = capacity(productiveDays = 1, target = "1").netProductionMeters.toPlainString()
        val result = calculate(startDate = date(2026, 10, 4), target = oneDayNet)

        assertEquals(date(2026, 10, 5), result.completionDate)
        assertEquals(listOf(date(2026, 10, 4)), result.calendar.nonProductiveDates)
    }

    @Test
    fun `D9 feriado especifico e resolvido classificado e desloca prazo`() {
        val holiday = date(2026, 10, 20)
        val result = calculate(holidays = listOf(SpecificDateHoliday("Evento", holiday)))

        assertEquals(setOf(holiday), result.resolvedHolidays)
        assertTrue(holiday in result.calendar.nonProductiveDates)
        assertEquals(date(2026, 10, 26), result.completionDate)
    }

    @Test
    fun `D10 feriado duplicado tem efeito unico`() {
        val holiday = date(2026, 10, 12)
        val result = calculate(
            holidays = listOf(
                AnnualHoliday("Anual", MonthDay.of(10, 12)),
                SpecificDateHoliday("Especifico", holiday),
            ),
        )

        assertEquals(setOf(holiday), result.resolvedHolidays)
        assertEquals(1, result.calendar.holidayCount)
        assertEquals(date(2026, 10, 26), result.completionDate)
    }

    @Test
    fun `D11 horas fracionarias preservam calculo deterministico`() {
        val result = calculate(hours = "7.5")

        assertEquals(31, result.requiredProductiveDays)
        assertEquals(date(2026, 11, 16), result.completionDate)
        assertDecimal("10148", result.capacity.netProductionMeters)
    }

    @Test
    fun `D12 meta zero e rejeitada`() {
        assertThrows(IllegalArgumentException::class.java) { calculate(target = "0") }
    }

    @Test
    fun `D13 meta negativa e rejeitada`() {
        assertThrows(IllegalArgumentException::class.java) { calculate(target = "-1") }
    }

    @Test
    fun `D14 meta fracionaria e rejeitada`() {
        assertThrows(IllegalArgumentException::class.java) { calculate(target = "10000.5") }
    }

    @Test
    fun `D15 parametros produtivos invalidos sao rejeitados antes da busca`() {
        assertThrows(IllegalArgumentException::class.java) { calculate(speed = "0") }
        assertThrows(IllegalArgumentException::class.java) { calculate(tapeCount = 0) }
        assertThrows(IllegalArgumentException::class.java) { calculate(hours = "0") }
        assertThrows(IllegalArgumentException::class.java) { calculate(waste = "100") }
    }

    @Test
    fun `D16 horizonte tecnico impede busca infinita`() {
        val allMonthDays = generateSequence(LocalDate.of(2028, 1, 1)) { current ->
            if (current < LocalDate.of(2028, 12, 31)) current.plusDays(1) else null
        }.map { AnnualHoliday(it.toString(), MonthDay.from(it)) }.toList()

        val exception = assertThrows(IllegalStateException::class.java) {
            calculate(holidays = allMonthDays)
        }

        assertEquals("Não foi possível estimar a conclusão dentro do horizonte suportado.", exception.message)
    }

    @Test
    fun `D17 resultado final preserva rastreabilidade`() {
        val holiday = date(2026, 10, 12)
        val result = calculate(holidays = listOf(AnnualHoliday("Feriado", MonthDay.of(10, 12))))

        assertEquals(result.requiredProductiveDays, result.calendar.productiveDays)
        assertEquals(result.completionDate, result.calendar.productiveDates.last())
        assertTrue(result.completionDate in result.calendar.consideredDates)
        assertEquals(setOf(holiday), result.resolvedHolidays)
        assertTrue(requireNotNull(result.capacity.balanceMeters) >= BigDecimal.ZERO)
    }

    @Test
    fun `D18 regressao com duas fitas encontra quantidade minima e primeira data`() {
        val result = calculate(tapeCount = 2)

        assertEquals(22, result.requiredProductiveDays)
        assertEquals(date(2026, 11, 3), result.completionDate)
        assertTrue(requireNotNull(capacity(21, "10000", tapeCount = 2).balanceMeters) < BigDecimal.ZERO)
        assertTrue(requireNotNull(result.capacity.balanceMeters) >= BigDecimal.ZERO)
    }

    private fun calculate(
        startDate: LocalDate = date(2026, 10, 5),
        includeStartDate: Boolean = true,
        workOnHolidays: Boolean = false,
        holidays: Collection<HolidayDefinition> = emptyList(),
        speed: String = "25",
        tapeCount: Int = 3,
        hours: String = "16",
        waste: String = "3",
        target: String = "10000",
    ) = ProductionDeadlineCalculator.calculate(
        ProductionDeadlineInput(
            startDate = startDate,
            includeStartDate = includeStartDate,
            includeSaturdays = false,
            includeSundays = false,
            workOnHolidays = workOnHolidays,
            holidayDefinitions = holidays,
            speedCmPerMinute = decimal(speed),
            tapeCount = tapeCount,
            productiveHoursPerDay = decimal(hours),
            wastePercent = decimal(waste),
            targetMeters = decimal(target),
        ),
    )

    private fun capacity(productiveDays: Int, target: String, tapeCount: Int = 3) =
        ProductionCapacityCalculator.calculate(
            ProductionCapacityInput(decimal("25"), tapeCount, decimal("16"), productiveDays, decimal("3"), decimal(target)),
        )

    private fun assertDecimal(expected: String, actual: BigDecimal) {
        assertTrue("Esperado $expected, obtido $actual", decimal(expected).compareTo(actual) == 0)
    }

    private fun decimal(value: String) = BigDecimal(value)

    private fun date(year: Int, month: Int, day: Int): LocalDate = LocalDate.of(year, month, day)
}

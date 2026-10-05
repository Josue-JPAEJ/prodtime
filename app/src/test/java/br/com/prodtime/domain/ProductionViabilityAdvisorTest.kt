package br.com.prodtime.domain

import java.math.BigDecimal
import java.time.LocalDate
import java.time.MonthDay
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class ProductionViabilityAdvisorTest {
    @Test
    fun `V1 configuracao atual atende`() {
        val result = evaluate()

        assertTrue(result.meetsTarget)
        assertDecimal("13270", result.currentEstimate.netProductionMeters)
        assertDecimal("3270", result.differenceMeters)
        assertEquals(3, result.minimumTapeCount)
        assertEquals(0, result.additionalTapesNeeded)
    }

    @Test
    fun `V2 configuracao atual nao atende`() {
        val result = evaluate(currentTapeCount = 2)

        assertTrue(!result.meetsTarget)
        assertDecimal("8846", result.currentEstimate.netProductionMeters)
        assertDecimal("-1154", result.differenceMeters)
        assertEquals(3, result.minimumTapeCount)
        assertEquals(1, result.additionalTapesNeeded)
    }

    @Test
    fun `V3 minimo pode ser menor que configuracao atual`() {
        val result = evaluate(target = "8000")

        assertTrue(result.meetsTarget)
        assertEquals(2, result.minimumTapeCount)
        assertEquals(0, result.additionalTapesNeeded)
    }

    @Test
    fun `V4 meta exata atende com diferenca zero`() {
        val result = evaluate(currentTapeCount = 2, target = "8846")

        assertTrue(result.meetsTarget)
        assertDecimal("0", result.differenceMeters)
        assertEquals(2, result.minimumTapeCount)
        assertEquals(0, result.additionalTapesNeeded)
    }

    @Test
    fun `V5 feriado nao trabalhado altera recomendacao`() {
        val result = evaluate(
            target = "12600",
            holidays = listOf(AnnualHoliday("Feriado", MonthDay.of(10, 5))),
        )

        assertEquals(18, result.currentEstimate.calendar.productiveDays)
        assertDecimal("12571", result.currentEstimate.netProductionMeters)
        assertEquals(4, result.minimumTapeCount)
        assertEquals(1, result.additionalTapesNeeded)
    }

    @Test
    fun `V6 feriado trabalhado restaura recomendacao`() {
        val holiday = AnnualHoliday("Feriado", MonthDay.of(10, 5))
        val result = evaluate(target = "12600", holidays = listOf(holiday), workOnHolidays = true)

        assertEquals(19, result.currentEstimate.calendar.productiveDays)
        assertEquals(3, result.minimumTapeCount)
        assertEquals(0, result.additionalTapesNeeded)
    }

    @Test
    fun `V7 zero dias produtivos nao recomenda fitas`() {
        val sunday = date(2026, 10, 4)
        val result = evaluate(startDate = sunday, endDate = sunday, target = "1000")

        assertDecimal("0", result.currentEstimate.netProductionMeters)
        assertTrue(!result.meetsTarget)
        assertDecimal("-1000", result.differenceMeters)
        assertNull(result.minimumTapeCount)
        assertNull(result.additionalTapesNeeded)
    }

    @Test
    fun `V8 meta zero e rejeitada`() {
        assertThrows(IllegalArgumentException::class.java) { evaluate(target = "0") }
    }

    @Test
    fun `V9 meta negativa e rejeitada`() {
        assertThrows(IllegalArgumentException::class.java) { evaluate(target = "-1") }
    }

    @Test
    fun `V10 meta fracionaria e rejeitada`() {
        assertThrows(IllegalArgumentException::class.java) { evaluate(target = "1.5") }
    }

    @Test
    fun `V11 parametros produtivos invalidos sao rejeitados mesmo sem dias produtivos`() {
        val sunday = date(2026, 10, 4)

        assertThrows(IllegalArgumentException::class.java) {
            evaluate(startDate = sunday, endDate = sunday, speed = "0")
        }
    }

    @Test
    fun `V12 recomendacao e minima`() {
        val result = evaluate(currentTapeCount = 2)
        val days = result.currentEstimate.calendar.productiveDays

        assertTrue(capacity(2, days).netProductionMeters < decimal("10000"))
        assertTrue(capacity(3, days).netProductionMeters >= decimal("10000"))
        assertEquals(3, result.minimumTapeCount)
    }

    @Test
    fun `V13 horas fracionarias sao preservadas`() {
        val result = evaluate(hours = "7.5", currentTapeCount = 6)
        val minimum = requireNotNull(result.minimumTapeCount)
        val days = result.currentEstimate.calendar.productiveDays

        assertEquals(5, minimum)
        assertTrue(capacity(minimum - 1, days, hours = "7.5").netProductionMeters < decimal("10000"))
        assertTrue(capacity(minimum, days, hours = "7.5").netProductionMeters >= decimal("10000"))
    }

    @Test
    fun `V14 feriados coincidentes tem efeito unico`() {
        val holiday = date(2026, 10, 5)
        val result = evaluate(
            target = "12600",
            holidays = listOf(
                AnnualHoliday("Anual", MonthDay.of(10, 5)),
                SpecificDateHoliday("Especifico", holiday),
            ),
        )

        assertEquals(setOf(holiday), result.currentEstimate.resolvedHolidays)
        assertEquals(1, result.currentEstimate.calendar.holidayCount)
        assertEquals(4, result.minimumTapeCount)
    }

    @Test
    fun `V15 intervalo invertido e rejeitado pelo fluxo existente`() {
        assertThrows(IllegalArgumentException::class.java) {
            evaluate(startDate = END_DATE, endDate = START_DATE)
        }
    }

    @Test
    fun `V16 limite tecnico de fitas gera erro claro`() {
        val exception = assertThrows(IllegalStateException::class.java) {
            evaluate(target = "999999999999999999999999999999999999999999999999")
        }

        assertEquals("Não foi possível atingir a meta dentro do limite técnico suportado.", exception.message)
    }

    @Test
    fun `V17 resultado preserva rastreabilidade`() {
        val holiday = date(2026, 10, 5)
        val result = evaluate(holidays = listOf(SpecificDateHoliday("Evento", holiday)))

        assertEquals(setOf(holiday), result.currentEstimate.resolvedHolidays)
        assertTrue(holiday in result.currentEstimate.calendar.nonProductiveDates)
        assertTrue(result.currentEstimate.capacity != null)
        assertDecimal(
            result.currentEstimate.netProductionMeters.subtract(result.targetMeters).toPlainString(),
            result.differenceMeters,
        )
    }

    @Test
    fun `V18 candidatos permanecem coerentes com motor de capacidade`() {
        val result = evaluate(currentTapeCount = 2)
        val minimum = requireNotNull(result.minimumTapeCount)
        val days = result.currentEstimate.calendar.productiveDays

        val previous = capacity(minimum - 1, days)
        val recommended = capacity(minimum, days)
        assertTrue(previous.netProductionMeters < result.targetMeters)
        assertTrue(recommended.netProductionMeters >= result.targetMeters)
        assertDecimal("8846", previous.netProductionMeters)
        assertDecimal("13270", recommended.netProductionMeters)
    }

    private fun evaluate(
        startDate: LocalDate = START_DATE,
        endDate: LocalDate = END_DATE,
        workOnHolidays: Boolean = false,
        holidays: Collection<HolidayDefinition> = emptyList(),
        speed: String = "25",
        currentTapeCount: Int = 3,
        hours: String = "16",
        target: String = "10000",
    ) = ProductionViabilityAdvisor.evaluate(
        ProductionViabilityInput(
            startDate = startDate,
            endDate = endDate,
            includeStartDate = true,
            includeEndDate = true,
            includeSaturdays = false,
            includeSundays = false,
            workOnHolidays = workOnHolidays,
            holidayDefinitions = holidays,
            speedCmPerMinute = decimal(speed),
            currentTapeCount = currentTapeCount,
            productiveHoursPerDay = decimal(hours),
            wastePercent = decimal("3"),
            targetMeters = decimal(target),
        ),
    )

    private fun capacity(tapeCount: Int, productiveDays: Int, hours: String = "16") =
        ProductionCapacityCalculator.calculate(
            ProductionCapacityInput(
                speedCmPerMinute = decimal("25"),
                tapeCount = tapeCount,
                productiveHoursPerDay = decimal(hours),
                productiveDays = productiveDays,
                wastePercent = decimal("3"),
            ),
        )

    private fun assertDecimal(expected: String, actual: BigDecimal) {
        assertTrue("Esperado $expected, obtido $actual", decimal(expected).compareTo(actual) == 0)
    }

    private fun decimal(value: String) = BigDecimal(value)

    private fun date(year: Int, month: Int, day: Int): LocalDate = LocalDate.of(year, month, day)

    private companion object {
        val START_DATE: LocalDate = LocalDate.of(2026, 10, 1)
        val END_DATE: LocalDate = LocalDate.of(2026, 10, 27)
    }
}

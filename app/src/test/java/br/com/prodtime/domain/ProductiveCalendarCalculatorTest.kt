package br.com.prodtime.domain

import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class ProductiveCalendarCalculatorTest {
    @Test
    fun `dia util normal e produtivo`() {
        val result = calculate(MONDAY)

        assertEquals(1, result.consideredDays)
        assertEquals(1, result.productiveDays)
    }

    @Test
    fun `domingo nao trabalhado nao e produtivo`() {
        val result = calculate(SUNDAY, includeSundays = false)

        assertEquals(0, result.productiveDays)
        assertEquals(listOf(SUNDAY), result.nonProductiveDates)
    }

    @Test
    fun `domingo trabalhado e produtivo quando nao e feriado`() {
        val result = calculate(SUNDAY, includeSundays = true)

        assertEquals(1, result.productiveDays)
    }

    @Test
    fun `domingo feriado nao e subtraido duas vezes`() {
        val result = calculate(SUNDAY, holidays = setOf(SUNDAY))

        assertEquals(1, result.consideredDays)
        assertEquals(0, result.productiveDays)
        assertEquals(1, result.sundayCount)
        assertEquals(1, result.holidayCount)
        assertTrue(result.productiveDays >= 0)
    }

    @Test
    fun `feriado nao trabalhado prevalece sobre sabado trabalhado`() {
        val result = calculate(SATURDAY, includeSaturdays = true, holidays = setOf(SATURDAY))

        assertEquals(0, result.productiveDays)
    }

    @Test
    fun `sabado feriado e produtivo quando ambas as politicas permitem`() {
        val result = calculate(
            SATURDAY,
            includeSaturdays = true,
            workOnHolidays = true,
            holidays = setOf(SATURDAY),
        )

        assertEquals(1, result.productiveDays)
    }

    @Test
    fun `data inicial excluida nao participa de listas nem contagens`() {
        val result = calculate(
            startDate = SATURDAY,
            endDate = MONDAY,
            includeStartDate = false,
            holidays = setOf(SATURDAY),
        )

        assertDateIsAbsent(SATURDAY, result)
        assertEquals(0, result.saturdayCount)
        assertEquals(0, result.holidayCount)
    }

    @Test
    fun `data final excluida nao participa de listas nem contagens`() {
        val result = calculate(
            startDate = MONDAY,
            endDate = SATURDAY.plusWeeks(1),
            includeEndDate = false,
            holidays = setOf(SATURDAY.plusWeeks(1)),
        )

        assertDateIsAbsent(SATURDAY.plusWeeks(1), result)
        assertEquals(0, result.saturdayCount)
        assertEquals(0, result.holidayCount)
    }

    @Test
    fun `domingo feriado excluido na ponta inicial nao e classificado`() {
        val result = calculate(
            startDate = SUNDAY,
            endDate = MONDAY,
            includeStartDate = false,
            holidays = setOf(SUNDAY),
        )

        assertDateIsAbsent(SUNDAY, result)
        assertEquals(0, result.sundayCount)
        assertEquals(0, result.holidayCount)
        assertEquals(1, result.productiveDays)
        assertTrue(result.nonProductiveDates.isEmpty())
    }

    @Test
    fun `data unica permanece quando ambas as pontas sao incluidas`() {
        assertEquals(1, calculate(MONDAY).consideredDays)
    }

    @Test
    fun `data unica e removida quando somente a ponta inicial e excluida`() {
        assertEquals(0, calculate(MONDAY, includeStartDate = false).consideredDays)
    }

    @Test
    fun `data unica e removida quando somente a ponta final e excluida`() {
        assertEquals(0, calculate(MONDAY, includeEndDate = false).consideredDays)
    }

    @Test
    fun `data unica e removida quando ambas as pontas sao excluidas`() {
        val result = calculate(MONDAY, includeStartDate = false, includeEndDate = false)

        assertEquals(0, result.consideredDays)
        assertEquals(0, result.productiveDays)
    }

    @Test
    fun `set construido de duplicatas contabiliza feriado uma vez`() {
        val holidays = listOf(MONDAY, MONDAY, MONDAY).toSet()

        val result = calculate(MONDAY, holidays = holidays)

        assertEquals(1, result.holidayCount)
        assertEquals(0, result.productiveDays)
    }

    @Test
    fun `intervalo invertido e rejeitado com mensagem clara`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            calculate(startDate = MONDAY, endDate = SUNDAY)
        }

        assertEquals("A data inicial deve ser anterior ou igual à data final.", exception.message)
    }

    @Test
    fun `intervalo valido pode ter zero dias produtivos`() {
        val result = calculate(startDate = SATURDAY, endDate = SUNDAY)

        assertTrue(result.consideredDays >= 0)
        assertEquals(0, result.productiveDays)
    }

    @Test
    fun `intervalo multiplo preserva ordem classificacoes e contagens`() {
        val holiday = MONDAY.plusDays(2)
        val weekSunday = MONDAY.plusDays(6)
        val result = calculate(startDate = MONDAY, endDate = weekSunday, holidays = setOf(holiday))

        assertEquals((0L..6L).map(MONDAY::plusDays), result.consideredDates)
        assertEquals(7, result.consideredDays)
        assertEquals(4, result.productiveDays)
        assertEquals(listOf(MONDAY, MONDAY.plusDays(1), MONDAY.plusDays(3), MONDAY.plusDays(4)), result.productiveDates)
        assertEquals(listOf(holiday, MONDAY.plusDays(5), weekSunday), result.nonProductiveDates)
        assertEquals(1, result.saturdayCount)
        assertEquals(1, result.sundayCount)
        assertEquals(1, result.holidayCount)
    }

    @Test
    fun `feriado fora do intervalo nao produz efeito`() {
        val result = calculate(MONDAY, holidays = setOf(MONDAY.minusDays(1)))

        assertEquals(0, result.holidayCount)
        assertEquals(1, result.productiveDays)
    }

    @Test
    fun `feriado em dia util e produtivo quando trabalho em feriados e permitido`() {
        val result = calculate(MONDAY, workOnHolidays = true, holidays = setOf(MONDAY))

        assertEquals(1, result.productiveDays)
        assertEquals(1, result.holidayCount)
    }

    @Test
    fun `trabalho em feriado nao torna domingo nao trabalhado produtivo`() {
        val result = calculate(
            SUNDAY,
            includeSundays = false,
            workOnHolidays = true,
            holidays = setOf(SUNDAY),
        )

        assertEquals(0, result.productiveDays)
        assertEquals(listOf(SUNDAY), result.nonProductiveDates)
    }

    @Test
    fun `listas formam particao exata das datas consideradas`() {
        val result = calculate(startDate = SATURDAY, endDate = MONDAY, holidays = setOf(SUNDAY))

        assertEquals(result.consideredDays, result.consideredDates.size)
        assertEquals(result.productiveDays, result.productiveDates.size)
        assertEquals(result.consideredDates, (result.productiveDates + result.nonProductiveDates).sorted())
        assertTrue(result.productiveDates.toSet().intersect(result.nonProductiveDates.toSet()).isEmpty())
        assertEquals(result.consideredDates.size, result.consideredDates.toSet().size)
        assertTrue(result.productiveDays in 0..result.consideredDays)
    }

    private fun calculate(
        startDate: LocalDate,
        endDate: LocalDate = startDate,
        includeStartDate: Boolean = true,
        includeEndDate: Boolean = true,
        includeSaturdays: Boolean = false,
        includeSundays: Boolean = false,
        workOnHolidays: Boolean = false,
        holidays: Set<LocalDate> = emptySet(),
    ) = ProductiveCalendarCalculator.calculate(
        ProductiveCalendarInput(
            startDate = startDate,
            endDate = endDate,
            includeStartDate = includeStartDate,
            includeEndDate = includeEndDate,
            includeSaturdays = includeSaturdays,
            includeSundays = includeSundays,
            workOnHolidays = workOnHolidays,
            holidays = holidays,
        ),
    )

    private fun assertDateIsAbsent(date: LocalDate, result: ProductiveCalendarResult) {
        assertFalse(date in result.consideredDates)
        assertFalse(date in result.productiveDates)
        assertFalse(date in result.nonProductiveDates)
    }

    private companion object {
        val SATURDAY: LocalDate = LocalDate.of(2026, 10, 3)
        val SUNDAY: LocalDate = LocalDate.of(2026, 10, 4)
        val MONDAY: LocalDate = LocalDate.of(2026, 10, 5)
    }
}

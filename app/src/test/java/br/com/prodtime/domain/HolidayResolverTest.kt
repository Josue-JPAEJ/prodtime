package br.com.prodtime.domain

import java.time.LocalDate
import java.time.MonthDay
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class HolidayResolverTest {
    @Test
    fun `H1 feriado anual e resolvido no mesmo ano`() {
        assertEquals(setOf(date(2026, 12, 25)), resolve(natal, date(2026, 12, 1), date(2026, 12, 31)))
    }

    @Test
    fun `H2 feriado anual e resolvido em ano diferente`() {
        assertEquals(setOf(date(2027, 12, 25)), resolve(natal, date(2027, 12, 1), date(2027, 12, 31)))
    }

    @Test
    fun `H3 intervalo entre anos gera uma ocorrencia anual por ano`() {
        assertEquals(
            setOf(date(2026, 12, 25), date(2027, 12, 25)),
            resolve(natal, date(2026, 12, 1), date(2027, 12, 31)),
        )
    }

    @Test
    fun `H4 data especifica dentro do intervalo e incluida`() {
        val carnaval = SpecificDateHoliday("Carnaval 2027", date(2027, 2, 9))

        assertEquals(setOf(date(2027, 2, 9)), resolve(carnaval, date(2027, 2, 1), date(2027, 2, 28)))
    }

    @Test
    fun `H5 data especifica fora do intervalo e ignorada`() {
        val carnaval = SpecificDateHoliday("Carnaval 2027", date(2027, 2, 9))

        assertTrue(resolve(carnaval, date(2027, 3, 1), date(2027, 3, 31)).isEmpty())
    }

    @Test
    fun `H6 definicoes diferentes na mesma data sao deduplicadas`() {
        val specific = SpecificDateHoliday("Evento especial", date(2026, 12, 25))

        assertEquals(setOf(date(2026, 12, 25)), resolve(listOf(natal, specific), date(2026, 1, 1), date(2026, 12, 31)))
    }

    @Test
    fun `H7 intervalo de uma data inclui feriado coincidente`() {
        assertEquals(setOf(date(2026, 12, 25)), resolve(natal, date(2026, 12, 25), date(2026, 12, 25)))
    }

    @Test
    fun `H8 intervalo de uma data sem feriado retorna vazio`() {
        assertTrue(resolve(natal, date(2026, 12, 24), date(2026, 12, 24)).isEmpty())
    }

    @Test
    fun `H9 intervalo invertido e rejeitado`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            resolve(natal, date(2027, 1, 1), date(2026, 1, 1))
        }

        assertEquals("A data inicial deve ser anterior ou igual à data final.", exception.message)
    }

    @Test
    fun `H10 feriado anual em 29 de fevereiro ocorre em ano bissexto`() {
        val leapDay = AnnualHoliday("Data bissexta", MonthDay.of(2, 29))

        assertEquals(setOf(date(2028, 2, 29)), resolve(leapDay, date(2028, 1, 1), date(2028, 12, 31)))
    }

    @Test
    fun `H11 feriado anual em 29 de fevereiro nao ocorre em ano comum`() {
        val leapDay = AnnualHoliday("Data bissexta", MonthDay.of(2, 29))

        assertTrue(resolve(leapDay, date(2027, 1, 1), date(2027, 12, 31)).isEmpty())
    }

    @Test
    fun `H12 multiplos feriados retornam somente datas aplicaveis`() {
        val definitions = listOf(
            natal,
            AnnualHoliday("Ano-novo", MonthDay.of(1, 1)),
            SpecificDateHoliday("Evento interno", date(2027, 6, 15)),
            SpecificDateHoliday("Evento fora", date(2028, 6, 15)),
        )

        assertEquals(
            setOf(date(2027, 1, 1), date(2027, 6, 15), date(2027, 12, 25)),
            resolve(definitions, date(2027, 1, 1), date(2027, 12, 31)),
        )
    }

    @Test
    fun `nome vazio ou composto somente por espacos e rejeitado`() {
        assertThrows(IllegalArgumentException::class.java) { AnnualHoliday("", MonthDay.of(1, 1)) }
        assertThrows(IllegalArgumentException::class.java) { SpecificDateHoliday("   ", date(2027, 1, 1)) }
    }

    @Test
    fun `feriados resolvidos alimentam diretamente o calendario`() {
        val christmas = date(2026, 12, 25)
        val holidays = resolve(natal, christmas, christmas)

        val result = ProductiveCalendarCalculator.calculate(
            ProductiveCalendarInput(
                startDate = christmas,
                endDate = christmas,
                includeStartDate = true,
                includeEndDate = true,
                includeSaturdays = false,
                includeSundays = false,
                workOnHolidays = false,
                holidays = holidays,
            ),
        )

        assertEquals(1, result.holidayCount)
        assertEquals(0, result.productiveDays)
        assertEquals(listOf(christmas), result.nonProductiveDates)
    }

    private fun resolve(definition: HolidayDefinition, startDate: LocalDate, endDate: LocalDate) =
        resolve(listOf(definition), startDate, endDate)

    private fun resolve(
        definitions: Collection<HolidayDefinition>,
        startDate: LocalDate,
        endDate: LocalDate,
    ) = HolidayResolver.resolve(definitions, startDate, endDate)

    private fun date(year: Int, month: Int, day: Int): LocalDate = LocalDate.of(year, month, day)

    private companion object {
        val natal = AnnualHoliday("Natal", MonthDay.of(12, 25))
    }
}

package br.com.prodtime.ui

import br.com.prodtime.domain.ProductiveCalendarResult
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

class ResultPresentationTest {
    private val monday = LocalDate.of(2026, 10, 5)
    private val saturday = LocalDate.of(2026, 10, 10)
    private val sunday = LocalDate.of(2026, 10, 11)

    @Test
    fun `calendario sem classificacoes produtivas nao cria linhas`() {
        assertEquals(emptyList<CalendarInclusionRow>(), calendarIncludedDays(calendar(monday), emptySet()))
    }

    @Test
    fun `sabado produtivo cria linha com pluralizacao`() {
        assertEquals(
            listOf(CalendarInclusionRow("Sábados", 2)),
            calendarIncludedDays(calendar(saturday, saturday.plusWeeks(1)), emptySet()),
        )
    }

    @Test
    fun `domingo produtivo cria linha singular`() {
        assertEquals(
            listOf(CalendarInclusionRow("Domingo", 1)),
            calendarIncludedDays(calendar(sunday), emptySet()),
        )
    }

    @Test
    fun `feriado produtivo cria linha`() {
        assertEquals(
            listOf(CalendarInclusionRow("Feriado", 1)),
            calendarIncludedDays(calendar(monday), setOf(monday)),
        )
    }

    @Test
    fun `feriado excluido nao cria linha`() {
        assertEquals(
            emptyList<CalendarInclusionRow>(),
            calendarIncludedDays(calendar(), setOf(monday)),
        )
    }

    @Test
    fun `colisao entre sabado e feriado preserva as duas classificacoes`() {
        val calendar = calendar(saturday)

        assertEquals(
            listOf(CalendarInclusionRow("Feriado", 1), CalendarInclusionRow("Sábado", 1)),
            calendarIncludedDays(calendar, setOf(saturday)),
        )
        assertEquals(1, calendar.productiveDays)
    }

    @Test
    fun `gera texto copiado do modo A`() {
        assertEquals(
            """Produção estimada
                |13.270 m
                |Dias produtivos: 19
                |Produção bruta: 13.680 m
                |Desperdício: 410,4 m
                |Período: 01/10/2026 a 27/10/2026""".trimMargin(),
            resultCopyText(
                "Produção estimada",
                "13.270 m",
                listOf(
                    "Dias produtivos" to "19",
                    "Produção bruta" to "13.680 m",
                    "Desperdício" to "410,4 m",
                    "Período" to "01/10/2026 a 27/10/2026",
                ),
            ),
        )
    }

    @Test
    fun `gera texto copiado do prazo`() {
        assertEquals(
            """Conclusão estimada
                |23/10/2026
                |Meta: 10.000 m
                |Dias produtivos: 15
                |Produção: 10.476 m
                |Saldo: +476 m""".trimMargin(),
            resultCopyText(
                "Conclusão estimada",
                "23/10/2026",
                listOf(
                    "Meta" to "10.000 m",
                    "Dias produtivos" to "15",
                    "Produção" to "10.476 m",
                    "Saldo" to "+476 m",
                ),
            ),
        )
    }

    @Test
    fun `gera texto copiado da viabilidade com resumo de calendario`() {
        assertEquals(
            """Meta atendida
                |Produção: 13.270 m
                |Dias produtivos: 24
                |Feriado: 1
                |Sábados: 3
                |Meta: 10.000 m
                |Excedente: +3.270 m
                |Mínimo necessário: 3 fitas
                |Fitas adicionais: 0""".trimMargin(),
            resultCopyText(
                "Meta atendida",
                "13.270 m",
                listOf(
                    "Dias produtivos" to "24",
                    "Feriado" to "1",
                    "Sábados" to "3",
                    "Meta" to "10.000 m",
                    "Excedente" to "+3.270 m",
                    "Mínimo necessário" to "3 fitas",
                    "Fitas adicionais" to "0",
                ),
                primaryLabel = "Produção",
            ),
        )
    }

    private fun calendar(vararg productiveDates: LocalDate) = ProductiveCalendarResult(
        consideredDays = productiveDates.size,
        productiveDays = productiveDates.size,
        consideredDates = productiveDates.toList(),
        productiveDates = productiveDates.toList(),
        nonProductiveDates = emptyList(),
        saturdayCount = 0,
        sundayCount = 0,
        holidayCount = 0,
    )
}

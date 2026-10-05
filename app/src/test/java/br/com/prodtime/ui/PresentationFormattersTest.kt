package br.com.prodtime.ui

import java.math.BigDecimal
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class PresentationFormattersTest {
    @Test
    fun `aceita decimal com virgula`() {
        assertEquals(BigDecimal("7.5"), parseDecimalInput("7,5"))
    }

    @Test
    fun `aceita decimal com ponto`() {
        assertEquals(BigDecimal("7.5"), parseDecimalInput("7.5"))
    }

    @Test
    fun `aceita inteiro positivo`() {
        assertEquals(3, parsePositiveIntInput("3"))
    }

    @Test
    fun `rejeita inteiro invalido`() {
        assertNull(parsePositiveIntInput("1,5"))
        assertNull(parsePositiveIntInput("0"))
        assertNull(parsePositiveIntInput("abc"))
    }

    @Test
    fun `formata metros com locale brasileiro e preserva casas relevantes`() {
        assertEquals("13.270 m", formatMeters(BigDecimal("13270")))
        assertEquals("410,4 m", formatMeters(BigDecimal("410.40")))
        assertEquals("0 m", formatMeters(BigDecimal.ZERO))
    }

    @Test
    fun `formata data brasileira`() {
        assertEquals("01/10/2026", formatDate(LocalDate.of(2026, 10, 1)))
    }

    @Test
    fun `apresenta deficit sem sinal negativo`() {
        assertEquals(DifferencePresentation("Déficit", BigDecimal("1154")), presentDifference(BigDecimal("-1154")))
    }

    @Test
    fun `apresenta excedente e diferenca zero`() {
        assertEquals(DifferencePresentation("Excedente", BigDecimal("3270")), presentDifference(BigDecimal("3270")))
        assertEquals(DifferencePresentation("Diferença", BigDecimal.ZERO), presentDifference(BigDecimal.ZERO))
    }
}

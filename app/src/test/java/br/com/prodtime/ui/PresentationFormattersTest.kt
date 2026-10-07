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
    fun `remove espacos comuns tabs e quebras de linha`() {
        assertEquals("25", sanitizeNumericInput(" \t2\n5\r "))
        assertEquals(BigDecimal("25"), parseDecimalInput(" \t2\n5\r "))
    }

    @Test
    fun `remove espacos unicode e caracteres invisiveis`() {
        assertEquals("25", sanitizeNumericInput("\u00A02\u202F5\u200B\u200C\u200D\u2060\uFEFF"))
        assertEquals(BigDecimal("25"), parseDecimalInput("25\u200B\uFEFF"))
    }

    @Test
    fun `aceita decimal com virgula cercado por espacos`() {
        assertEquals(BigDecimal("7.5"), parseDecimalInput(" 7,5 "))
    }

    @Test
    fun `nao remove texto alfabetico`() {
        assertEquals("abc", sanitizeNumericInput(" abc "))
        assertNull(parseDecimalInput("abc"))
        assertNull(parsePositiveIntInput("abc"))
    }

    @Test
    fun `nao converte silenciosamente texto misturado com numero`() {
        assertEquals("abc25", sanitizeNumericInput(" abc 25 "))
        assertNull(parseDecimalInput("abc25"))
        assertNull(parsePositiveIntInput("abc25"))
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

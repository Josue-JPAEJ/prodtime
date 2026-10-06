package br.com.prodtime.ui

import java.math.BigDecimal
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class InputValidationTest {
    @Test
    fun `meta respeita limite exclusivo`() {
        assertNull(targetInputError(BigDecimal("999999999")))
        assertNotNull(targetInputError(BigDecimal("1000000000")))
    }

    @Test
    fun `fitas respeitam limite exclusivo`() {
        assertNull(tapeCountInputError(999_999))
        assertNotNull(tapeCountInputError(1_000_000))
    }

    @Test
    fun `horas aceitam 24 e rejeitam acima ou zero`() {
        assertNull(productiveHoursInputError(BigDecimal("24")))
        assertNotNull(productiveHoursInputError(BigDecimal("24.01")))
        assertNotNull(productiveHoursInputError(BigDecimal.ZERO))
    }

    @Test
    fun `desperdicio aceita intervalo de zero ate abaixo de cem`() {
        assertNull(wasteInputError(BigDecimal.ZERO))
        assertNull(wasteInputError(BigDecimal("99.99")))
        assertNotNull(wasteInputError(BigDecimal("100")))
    }

    @Test
    fun `velocidade respeita limite exclusivo`() {
        assertNull(speedInputError(BigDecimal("999.99")))
        assertNotNull(speedInputError(BigDecimal("1000")))
        assertNotNull(speedInputError(BigDecimal.ZERO))
    }
}

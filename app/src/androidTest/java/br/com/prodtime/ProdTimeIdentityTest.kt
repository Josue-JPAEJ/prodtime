package br.com.prodtime

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.espresso.Espresso.pressBack
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProdTimeIdentityTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun aboutShowsAcademicIdentityAndBothBackActionsReturnHome() {
        composeRule.onNodeWithText("Sobre o ProdTime").performScrollTo().performClick()
        listOf(
            "ProdTime",
            "Planejamento rápido de capacidade e prazo para produção de fitas têxteis.",
            "Josué Paulo Alexandrina",
            "Análise e Desenvolvimento de Sistemas (ADS)",
            "Gran Faculdade",
            "josue_jpaej@hotmail.com",
            "O ProdTime estima capacidade, prazo e viabilidade com base nos parâmetros produtivos informados. Os resultados são estimativas e não representam promessa operacional.",
        ).forEach { text ->
            composeRule.onNodeWithText(text).performScrollTo().assertIsDisplayed()
        }
        composeRule.onNodeWithText("Voltar").performScrollTo().performClick()
        composeRule.onNodeWithText("Quanto consigo produzir?").assertIsDisplayed()
        composeRule.onNodeWithText("Quando vou terminar?").assertIsDisplayed()
        composeRule.onNodeWithText("Verificar uma meta").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithText("Sobre o ProdTime").performScrollTo().performClick()
        pressBack()
        composeRule.onNodeWithText("Configurar feriados").performScrollTo().assertIsDisplayed()
    }
}

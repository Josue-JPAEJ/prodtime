package br.com.prodtime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.prodtime.ui.ProdTimeApp
import br.com.prodtime.ui.theme.ProdTimeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProdTimeTheme {
                ProdTimeApp()
            }
        }
    }
}

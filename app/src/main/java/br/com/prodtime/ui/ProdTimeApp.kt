package br.com.prodtime.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

private enum class Destination {
    Home,
    ProductionEstimate,
    ProductionDeadline,
}

@Composable
fun ProdTimeApp() {
    var destinationName by rememberSaveable { mutableStateOf(Destination.Home.name) }
    val destination = Destination.valueOf(destinationName)

    BackHandler(enabled = destination != Destination.Home) {
        destinationName = Destination.Home.name
    }

    when (destination) {
        Destination.Home -> HomeScreen(
            onProductionEstimate = { destinationName = Destination.ProductionEstimate.name },
            onProductionDeadline = { destinationName = Destination.ProductionDeadline.name },
        )

        Destination.ProductionEstimate -> ProductionEstimateScreen(
            onBack = { destinationName = Destination.Home.name },
        )

        Destination.ProductionDeadline -> ProductionDeadlineScreen(
            onBack = { destinationName = Destination.Home.name },
        )
    }
}

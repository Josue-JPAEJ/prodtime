package br.com.prodtime.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import br.com.prodtime.domain.HolidayDefinition

private enum class Destination {
    Home,
    ProductionEstimate,
    ProductionDeadline,
    ProductionViability,
    Holidays,
}

@Composable
fun ProdTimeApp() {
    var destinationName by rememberSaveable { mutableStateOf(Destination.Home.name) }
    val holidayDefinitions = remember { mutableStateListOf<HolidayDefinition>() }
    val destination = Destination.valueOf(destinationName)

    BackHandler(enabled = destination != Destination.Home) {
        destinationName = Destination.Home.name
    }

    when (destination) {
        Destination.Home -> HomeScreen(
            onProductionEstimate = { destinationName = Destination.ProductionEstimate.name },
            onProductionDeadline = { destinationName = Destination.ProductionDeadline.name },
            onProductionViability = { destinationName = Destination.ProductionViability.name },
            onHolidays = { destinationName = Destination.Holidays.name },
        )

        Destination.ProductionEstimate -> ProductionEstimateScreen(
            holidayDefinitions = holidayDefinitions,
            onBack = { destinationName = Destination.Home.name },
        )

        Destination.ProductionDeadline -> ProductionDeadlineScreen(
            holidayDefinitions = holidayDefinitions,
            onBack = { destinationName = Destination.Home.name },
        )

        Destination.ProductionViability -> ProductionViabilityScreen(
            holidayDefinitions = holidayDefinitions,
            onBack = { destinationName = Destination.Home.name },
        )

        Destination.Holidays -> HolidayScreen(
            holidayDefinitions = holidayDefinitions,
            onAdd = { holidayDefinitions.add(it) },
            onRemove = { holidayDefinitions.remove(it) },
            onBack = { destinationName = Destination.Home.name },
        )
    }
}

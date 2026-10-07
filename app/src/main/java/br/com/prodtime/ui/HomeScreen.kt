package br.com.prodtime.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.prodtime.R
import br.com.prodtime.ui.theme.ProdTimeTheme

@Composable
fun HomeScreen(
    onProductionEstimate: () -> Unit,
    onProductionDeadline: () -> Unit,
    onProductionViability: () -> Unit,
    onHolidays: () -> Unit,
    onAbout: () -> Unit,
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(stringResource(R.string.app_name), style = MaterialTheme.typography.displaySmall)
            Spacer(Modifier.height(8.dp))
            Text(
                stringResource(R.string.app_short_description),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(36.dp))
            HomeActionCard(
                title = "Quanto consigo produzir?",
                description = "Informe um período e veja a capacidade estimada.",
                onClick = onProductionEstimate,
            )
            Spacer(Modifier.height(16.dp))
            HomeActionCard(
                title = "Quando vou terminar?",
                description = "Informe uma quantidade e estime a data de conclusão.",
                onClick = onProductionDeadline,
            )
            Spacer(Modifier.height(16.dp))
            HomeActionCard(
                title = "Verificar uma meta",
                description = "Descubra se a produção atende à meta e quantas fitas são necessárias.",
                onClick = onProductionViability,
            )
            Spacer(Modifier.height(24.dp))
            TextButton(onClick = onHolidays) { Text("Configurar feriados") }
            TextButton(onClick = onAbout) { Text(stringResource(R.string.about_title)) }
        }
    }
}

@Composable
private fun HomeActionCard(title: String, description: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(6.dp))
                Text(
                    description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Text(
                "›",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.clearAndSetSemantics {},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    ProdTimeTheme(dynamicColor = false) {
        HomeScreen(
            onProductionEstimate = {},
            onProductionDeadline = {},
            onProductionViability = {},
            onHolidays = {},
            onAbout = {},
        )
    }
}

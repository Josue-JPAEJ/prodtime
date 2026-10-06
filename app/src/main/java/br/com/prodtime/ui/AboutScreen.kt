package br.com.prodtime.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.prodtime.R
import br.com.prodtime.ui.theme.ProdTimeTheme

@Composable
fun AboutScreen(onBack: () -> Unit) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            TextButton(onClick = onBack) { Text(stringResource(R.string.about_back)) }
            Text(
                stringResource(R.string.about_title),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.semantics { heading() },
            )
            Text(stringResource(R.string.app_name), style = MaterialTheme.typography.titleLarge)
            Text(
                stringResource(R.string.app_short_description),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(stringResource(R.string.about_developed_by), style = MaterialTheme.typography.titleMedium)
                Text(stringResource(R.string.about_author))
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(stringResource(R.string.about_academic_project), style = MaterialTheme.typography.titleMedium)
                Text(stringResource(R.string.about_course))
                Text(stringResource(R.string.about_institution))
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(stringResource(R.string.about_email_label), style = MaterialTheme.typography.titleMedium)
                SelectionContainer { Text(stringResource(R.string.about_email)) }
            }
            Text(
                stringResource(R.string.about_estimate_notice),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview(name = "Sobre claro", showBackground = true)
@Preview(name = "Sobre escuro", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Sobre com fonte ampliada", showBackground = true, widthDp = 320, heightDp = 480, fontScale = 1.5f)
@Composable
private fun AboutScreenPreview() {
    ProdTimeTheme(dynamicColor = false) { AboutScreen(onBack = {}) }
}

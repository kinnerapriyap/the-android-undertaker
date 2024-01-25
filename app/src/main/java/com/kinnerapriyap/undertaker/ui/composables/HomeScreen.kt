package com.kinnerapriyap.undertaker.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kinnerapriyap.undertaker.Undertake
import com.roudikk.guia.extensions.push
import com.roudikk.guia.extensions.requireLocalNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    undertakes: List<Undertake>,
) {
    val navigator = requireLocalNavigator()
    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        topBar = { TopAppBar(title = { Text("The Undertaker lives here.") }) }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
        ) {
            items(undertakes) { undertake ->
                OutlinedButton(
                    onClick = { navigator.push(undertake.navigationKey) }
                ) {
                    Text(text = stringResource(undertake.title))
                }
            }
        }
    }
}
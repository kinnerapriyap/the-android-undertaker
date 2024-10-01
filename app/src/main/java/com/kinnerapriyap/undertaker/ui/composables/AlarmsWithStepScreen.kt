package com.kinnerapriyap.undertaker.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.kinnerapriyap.undertaker.R
import com.roudikk.guia.extensions.pop
import com.roudikk.guia.extensions.requireLocalNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmsWithStepScreen(
    modifier: Modifier = Modifier,
) {
    val navigator = requireLocalNavigator()
    Scaffold(
        modifier = modifier.navigationBarsPadding(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.alarms_with_step_title)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.arrow_back_content_description)
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
}

@Composable
fun AlarmsWithStep() {

}

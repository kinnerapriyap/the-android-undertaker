package com.kinnerapriyap.undertaker.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kinnerapriyap.undertaker.R
import com.kinnerapriyap.undertaker.Undertake
import com.roudikk.guia.extensions.push
import com.roudikk.guia.extensions.requireLocalNavigator
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    undertakes: ImmutableList<Undertake>,
    modifier: Modifier = Modifier,
) {
    val navigator = requireLocalNavigator()
    Scaffold(
        modifier = modifier.navigationBarsPadding(),
        topBar = { TopAppBar(title = { Text("The Undertaker lives here.") }) }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
        ) {
            items(undertakes) { undertake ->
                ListItem(
                    modifier = Modifier
                        .clickable { navigator.push(undertake.navigationKey) }
                        .height(72.dp),
                    headlineContent = {
                        Text(
                            text = stringResource(undertake.title),
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    leadingContent = {
                        when (undertake) {
                            is Undertake.DottyCirclesAnimation ->
                                DottyCirclesAnimation(
                                    gridSize = 3,
                                    gridSpacing = 4.dp,
                                    circleSize = 10.dp,
                                    offsetSize = 1.dp
                                )

                            is Undertake.SlotMachineAnimation ->
                                OneSlotMachineAnimation(
                                    options = (1..7).map { it.toString() }.toImmutableList(),
                                    animationDuration = Long.MAX_VALUE,
                                    transitionDuration = 120L,
                                    textStyle = MaterialTheme.typography.bodyLarge,
                                    borderWidth = 2.dp
                                )

                            is Undertake.SlideToUnlock -> ActionCircle(
                                modifier = Modifier.size(48.dp),
                                isLoading = false
                            )
                        }
                    },
                    trailingContent = {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_right),
                            contentDescription = null
                        )
                    },
                    shadowElevation = 2.dp
                )
            }
        }
    }
}

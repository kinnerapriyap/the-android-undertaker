package com.kinnerapriyap.undertaker.ui.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kinnerapriyap.undertaker.R
import com.roudikk.guia.extensions.pop
import com.roudikk.guia.extensions.requireLocalNavigator
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlotMachineAnimationScreen(
    modifier: Modifier = Modifier
) {
    val navigator = requireLocalNavigator()
    Scaffold(
        modifier = modifier.navigationBarsPadding(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.slot_machine_animation_title)) },
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
        var animationDuration by remember { mutableLongStateOf(Random.nextLong(2000L, 5000L)) }
        Column(
            modifier = Modifier.padding(it).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            listOf("A", "B", "C", "D", "E", "F").chunked(2).forEach { (first, second) ->
                Row {
                    OneSlotMachineAnimation(
                        options = (1..6).map { no -> "$first$no" }.toImmutableList(),
                        animationDuration = animationDuration,
                        transitionDuration = Random.nextLong(100L, 150L)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    OneSlotMachineAnimation(
                        options = (1..6).map { no -> "$second$no" }.toImmutableList(),
                        animationDuration = animationDuration,
                        transitionDuration = Random.nextLong(100L, 150L)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            val all = listOf("A", "B", "C", "D", "E", "F")
                .map { letter -> (1..6).map { no -> "$letter$no" } }
                .flatten()
            OneSlotMachineAnimation(
                options = all.toImmutableList(),
                animationDuration = animationDuration,
                transitionDuration = Random.nextLong(100L, 150L)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = {
                animationDuration = Random.nextLong(2000L, 5000L)
            }) {
                Text("Roll!")
            }
        }
    }
}

@Composable
fun OneSlotMachineAnimation(
    options: ImmutableList<String>,
    animationDuration: Long,
    transitionDuration: Long,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    borderWidth: Dp = 4.dp,
) {
    val optionsLength = options.size
    var targetOffset by remember { mutableIntStateOf((animationDuration / transitionDuration).toInt()) }
    var current by remember { mutableIntStateOf(0) }

    LaunchedEffect(animationDuration) {
        targetOffset = (animationDuration / transitionDuration).toInt()
        delay(transitionDuration)
        while (targetOffset > 0) {
            targetOffset--
            current = (current + 1) % optionsLength
            delay(transitionDuration)
        }
    }

    AnimatedContent(
        modifier = modifier
            .border(borderWidth, MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp)),
        targetState = current,
        transitionSpec = {
            slideInVertically(
                animationSpec = tween(transitionDuration.toInt()),
                initialOffsetY = { -it },
            ) togetherWith slideOutVertically(
                animationSpec = tween(transitionDuration.toInt()),
                targetOffsetY = { it }
            )
        }, label = ""
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = options[it],
            style = textStyle,
        )
    }
}

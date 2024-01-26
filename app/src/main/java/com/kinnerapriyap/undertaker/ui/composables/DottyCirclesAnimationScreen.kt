package com.kinnerapriyap.undertaker.ui.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.kinnerapriyap.undertaker.R
import com.roudikk.guia.extensions.pop
import com.roudikk.guia.extensions.requireLocalNavigator
import kotlin.math.roundToInt
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DottyCirclesAnimationScreen(
    modifier: Modifier = Modifier
) {
    val navigator = requireLocalNavigator()
    Scaffold(
        modifier = modifier.navigationBarsPadding(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.dotty_circles_animation_title)) },
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            DottyCirclesAnimation()
        }
    }
}

@Composable
fun DottyCirclesAnimation(
    modifier: Modifier = Modifier,
    gridSize: Int = 8,
    gridSpacing: Dp = 16.dp,
    circleSize: Dp = 24.dp,
    offsetSize: Dp = 4.dp,
) {
    val colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.error,
    )
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(gridSpacing))
        repeat(gridSize) {
            Row {
                Spacer(modifier = Modifier.width(gridSpacing))
                repeat(gridSize) {
                    DottyCircle(
                        fillColor = colors.random(),
                        delayMillis = Random.nextInt(0, 2000),
                        circleSize = circleSize,
                        offsetSize = offsetSize,
                    )
                    Spacer(modifier = Modifier.width(gridSpacing))
                }
            }
            Spacer(modifier = Modifier.height(gridSpacing))
        }
    }
}

@Composable
fun DottyCircle(
    fillColor: Color,
    delayMillis: Int,
    circleSize: Dp,
    offsetSize: Dp,
    modifier: Modifier = Modifier,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val pxToMove = with(LocalDensity.current) {
        offsetSize.toPx().roundToInt()
    }
    val offset by infiniteTransition.animateValue(
        initialValue = 0,
        targetValue = pxToMove,
        label = "offset",
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 700, delayMillis = 300, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(offsetMillis = delayMillis)
        ),
        typeConverter = Int.VectorConverter
    )
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .offset { IntOffset(offset, offset) }
                .size(circleSize)
                .clip(CircleShape)
                .background(fillColor)
        )
        Box(
            modifier = Modifier
                .offset { IntOffset(-offset, -offset) }
                .size(circleSize)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, MaterialTheme.colorScheme.onSurface, CircleShape)
        )
    }
}
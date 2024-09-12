package com.kinnerapriyap.undertaker.ui.composables

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.kinnerapriyap.undertaker.R
import com.kinnerapriyap.undertaker.ui.composables.SizeConstants.ActionCircle
import com.roudikk.guia.extensions.pop
import com.roudikk.guia.extensions.requireLocalNavigator
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlideToUnlockScreen(
    modifier: Modifier = Modifier
) {
    val navigator = requireLocalNavigator()
    Scaffold(
        modifier = modifier.navigationBarsPadding(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.slide_to_unlock_title)) },
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
            SlideToUnlock(isLoading = false)
        }
    }
}

private object SizeConstants {
    val ActionCircle = 60.dp
}

private const val SnapThreshold = 0.8f

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SlideToUnlock(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val density = LocalDensity.current
    val snapAnimationSpec = tween<Float>()
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val positionalThreshold = { distance: Float -> distance * SnapThreshold }
    val velocityThreshold = { with(density) { 100.dp.toPx() } }
    val state = rememberSaveable(
        density,
        saver = AnchoredDraggableState.Saver(
            snapAnimationSpec,
            decayAnimationSpec,
            positionalThreshold,
            velocityThreshold
        )
    ) {
        AnchoredDraggableState(
            initialValue = if (isLoading) Anchor.End else Anchor.Start,
            positionalThreshold,
            velocityThreshold,
            snapAnimationSpec,
            decayAnimationSpec
        )
    }
    val startToEndProgress = remember {
        derivedStateOf { state.progress(from = Anchor.Start, to = Anchor.End) }
    }
    val draggableSizePx = with(density) { ActionCircle.toPx() }
    Track(
        modifier = modifier,
        anchoredDraggableState = state,
        enabled = enabled,
        startColor = MaterialTheme.colorScheme.primary,
        endColor = MaterialTheme.colorScheme.tertiary,
        startToEndProgress = startToEndProgress.value,
        draggableSizePx = draggableSizePx,
    ) {
        ActionCircle(isLoading = isLoading)
    }
}

enum class Anchor { Start, End }


fun calculateTrackColor(startToEndProgress: Float, startColor: Color, endColor: Color): Color {
    val fraction = (startToEndProgress / SnapThreshold).coerceIn(0f..1f)
    return lerp(startColor, endColor, fraction)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Track(
    anchoredDraggableState: AnchoredDraggableState<Anchor>,
    startColor: Color,
    endColor: Color,
    enabled: Boolean,
    startToEndProgress: Float,
    draggableSizePx: Float,
    modifier: Modifier = Modifier,
    @StringRes hintRes: Int = R.string.slide_to_unlock_title,
    content: @Composable (BoxScope.() -> Unit),
) {
    val backgroundColor by remember(startToEndProgress) {
        derivedStateOf { calculateTrackColor(startToEndProgress, startColor, endColor) }
    }
    val hintTextColor by remember(startToEndProgress) {
        derivedStateOf {
            calculateTrackColor(
                startToEndProgress,
                Color.White,
                Color.White.copy(alpha = 0f)
            )
        }
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = backgroundColor, shape = CircleShape)
            .onSizeChanged { layoutSize ->
                val dragEndPoint = layoutSize.width - draggableSizePx
                anchoredDraggableState.updateAnchors(
                    DraggableAnchors {
                        Anchor.Start at 0f
                        Anchor.End at dragEndPoint
                    }
                )
            }
    ) {
        Box(
            modifier = Modifier
                .size(ActionCircle)
                .offset {
                    IntOffset(
                        x = anchoredDraggableState
                            .requireOffset()
                            .roundToInt(), y = 0
                    )
                }
                .anchoredDraggable(
                    state = anchoredDraggableState,
                    orientation = Orientation.Horizontal,
                    enabled = enabled,
                ),
            content = content
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(hintRes),
            color = hintTextColor,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun ActionCircle(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    @DrawableRes iconRes: Int = R.drawable.ic_arrow_right,
) {
    Box(
        modifier = modifier
            .size(ActionCircle)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
            .padding(2.dp)
            .background(color = backgroundColor, shape = CircleShape)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 2.dp
            )
        } else {
            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(iconRes),
                contentDescription = null,
            )
        }
    }
}

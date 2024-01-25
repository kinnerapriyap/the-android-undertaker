package com.kinnerapriyap.undertaker

import androidx.annotation.StringRes
import com.roudikk.guia.core.NavigationKey

data class Undertake(
    @StringRes val title: Int,
    val navigationKey: NavigationKey
)

internal val undertakes = listOf(
    Undertake(
        title = R.string.dotty_circles_animation_title,
        navigationKey = DottyCirclesAnimationKey,
    )
)

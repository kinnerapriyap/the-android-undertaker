package com.kinnerapriyap.undertaker

import androidx.annotation.StringRes
import com.roudikk.guia.core.NavigationKey

sealed interface Undertake {
    @get:StringRes
    val title: Int
    val navigationKey: NavigationKey

    data class DottyCirclesAnimationUndertake(
        override val title: Int = R.string.dotty_circles_animation_title,
        override val navigationKey: NavigationKey = DottyCirclesAnimationKey
    ) : Undertake

    data class SlotMachineAnimationUndertake(
        override val title: Int = R.string.slot_machine_animation_title,
        override val navigationKey: NavigationKey = SlotMachineAnimationKey
    ) : Undertake
}

internal val undertakes = listOf(
    Undertake.DottyCirclesAnimationUndertake(),
    Undertake.SlotMachineAnimationUndertake()
)

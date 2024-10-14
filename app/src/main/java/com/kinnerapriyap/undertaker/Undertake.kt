package com.kinnerapriyap.undertaker

import androidx.annotation.StringRes
import com.roudikk.guia.core.NavigationKey

sealed interface Undertake {
    @get:StringRes
    val title: Int
    val navigationKey: NavigationKey

    data class DottyCirclesAnimation(
        override val title: Int = R.string.dotty_circles_animation_title,
        override val navigationKey: NavigationKey = DottyCirclesAnimationKey
    ) : Undertake

    data class SlotMachineAnimation(
        override val title: Int = R.string.slot_machine_animation_title,
        override val navigationKey: NavigationKey = SlotMachineAnimationKey
    ) : Undertake

    data class SlideToUnlock(
        override val title: Int = R.string.slide_to_unlock_title,
        override val navigationKey: NavigationKey = SlideToUnlockKey
    ) : Undertake
}

internal val undertakes = listOf(
    Undertake.DottyCirclesAnimation(),
    Undertake.SlotMachineAnimation(),
    Undertake.SlideToUnlock(),
)

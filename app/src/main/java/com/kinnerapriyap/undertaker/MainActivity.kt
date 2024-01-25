package com.kinnerapriyap.undertaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kinnerapriyap.undertaker.ui.composables.DottyCirclesAnimationScreen
import com.kinnerapriyap.undertaker.ui.composables.HomeScreen
import com.kinnerapriyap.undertaker.ui.theme.TheAndroidUndertakerTheme
import com.roudikk.guia.containers.NavContainer
import com.roudikk.guia.core.NavigatorConfigBuilder
import com.roudikk.guia.core.rememberNavigator
import kotlinx.collections.immutable.toImmutableList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheAndroidUndertakerTheme(darkTheme = false) {
                val navigator = rememberNavigator(initialKey = HomeKey) { handleNavigation() }
                navigator.NavContainer()
            }
        }
    }
}

private fun NavigatorConfigBuilder.handleNavigation() {
    screen<HomeKey> { HomeScreen(undertakes = undertakes.toImmutableList()) }
    screen<DottyCirclesAnimationKey> { DottyCirclesAnimationScreen() }
}

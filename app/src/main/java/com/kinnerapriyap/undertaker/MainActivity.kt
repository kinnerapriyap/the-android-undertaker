package com.kinnerapriyap.undertaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kinnerapriyap.undertaker.ui.composables.DottyCirclesAnimationScreen
import com.kinnerapriyap.undertaker.ui.composables.HomeScreen
import com.kinnerapriyap.undertaker.ui.theme.TheandroidundertakerTheme
import com.roudikk.guia.containers.NavContainer
import com.roudikk.guia.core.NavigatorConfigBuilder
import com.roudikk.guia.core.rememberNavigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheandroidundertakerTheme {
                val navigator = rememberNavigator(initialKey = HomeKey) { handleNavigation() }
                navigator.NavContainer()
            }
        }
    }
}

private fun NavigatorConfigBuilder.handleNavigation() {
    screen<HomeKey> { HomeScreen(undertakes = undertakes) }
    screen<DottyCirclesAnimationKey> { DottyCirclesAnimationScreen() }
}

package dev.machado001.apptemplate.core.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.machado001.apptemplate.core.designsystem.theme.TemplateTheme
import dev.machado001.apptemplate.presentation.navigation.AppNavHost

@Composable
fun TemplateApp() {
    TemplateTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AppNavHost()
        }
    }
}

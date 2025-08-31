package com.example.apptransportes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.apptransportes.presentation.ui.navigation.AppNavigation
import com.example.apptransportes.presentation.ui.theme.AppTransportesTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val locale = Locale("es", "PE")
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)
        createConfigurationContext(config)
        enableEdgeToEdge()
        setContent {
            AppTransportesTheme {
                    AppNavigation()
            }
        }
    }
}
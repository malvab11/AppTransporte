package com.example.apptransportes.presentation.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apptransportes.presentation.ui.components.CommonButtons
import com.example.apptransportes.presentation.ui.components.CommonOutlinedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(padding: PaddingValues, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Configuración Inicial") }) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5))
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Inputs()
            Footer()
        }
    }
}

data class InputField(
    val label: String,
    val value: String,
    val preIcon: ImageVector,
    val onClick: () -> Unit
)

@Composable
private fun Inputs() {
    val fields = listOf(
        InputField(
            label = "Fecha",
            value = "",
            preIcon = Icons.Default.CalendarToday,
            onClick = { /* abrir DatePicker */ }
        ),
        InputField(
            label = "Empresa",
            value = "",
            preIcon = Icons.Default.Business,
            onClick = { /* abrir selector de empresas */ }
        ),
        InputField(
            label = "Ruta",
            value = "",
            preIcon = Icons.Default.LocationOn,
            onClick = { /* abrir mapa / dialogo */ }
        )
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        fields.forEach { field ->
            CommonOutlinedTextField(
                readOnly = true,
                label = field.label,
                value = field.value,
                onValueChange = {it -> field.onClick },
                icon = Icons.Default.ArrowDropDown,
                preIcon = field.preIcon
            )
        }
    }
}

@Composable
private fun Footer() {
    CommonButtons(
        title = "Registrar zona de trabajo",
        isEnabled = true,
        isLoading = false,
        onClick = {  }
    )
}

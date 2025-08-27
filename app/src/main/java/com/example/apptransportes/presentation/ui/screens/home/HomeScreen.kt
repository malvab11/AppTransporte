package com.example.apptransportes.presentation.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apptransportes.presentation.ui.components.CommonButtons
import com.example.apptransportes.presentation.ui.components.CommonInputCards

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsState()
    val openAlertDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Configuración Inicial") }) }
    ) { innerPadding ->
        if (openAlertDialog.value) {
            AlertDialog(
                onDismissRequest = { openAlertDialog.value = false },
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
            ) {
                Surface(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    shape = MaterialTheme.shapes.extraLarge, // más redondeado
                    tonalElevation = AlertDialogDefaults.TonalElevation
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                    ) {
                        // Título
                        Text(
                            text = "Selecciona una Empresa",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Lista de empresas
                        if (uiState.listEmpresas.isEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator()
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    "Cargando empresas...",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        } else {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier
                                    .heightIn(max = 250.dp) // limitar altura
                                    .padding(vertical = 8.dp)
                            ) {
                                items(uiState.listEmpresas) { empresa ->
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 4.dp)
                                            .background(Color.Transparent),
                                        shape = MaterialTheme.shapes.medium,
                                        tonalElevation = 1.dp,
                                        onClick = {
                                            // Acción al seleccionar empresa
                                            homeViewModel.onCompanySelected(empresa)
                                            openAlertDialog.value = false
                                        }
                                    ) {
                                        Text(
                                            text = empresa.nombre,
                                            modifier = Modifier.padding(12.dp),
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Botón de cerrar
                        CommonButtons(
                            title = "Cerrar",
                            isEnabled = true,
                            isLoading = false,
                            onClick = { openAlertDialog.value = false }
                        )
                    }
                }
            }
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5))
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Inputs(
                onCompanyClick = {
                    openAlertDialog.value = true
                    homeViewModel.getCompanies()
                },
                onRouteClick = {
                    openAlertDialog.value = true
                    homeViewModel.getRoutesByCompany()
                },
            )
            Footer(isEnabled = uiState.isEnabled)
        }
    }
}

data class InputField(
    val value: String,
    val preIcon: ImageVector,
    val onClick: () -> Unit
)

@Composable
private fun Inputs(onCompanyClick: () -> Unit, onRouteClick: () -> Unit) {
    val fields = listOf(
        InputField(
            value = "Fecha",
            preIcon = Icons.Default.CalendarToday,
            onClick = {}
        ),
        InputField(
            value = "Empresa",
            preIcon = Icons.Default.Business,
            onClick = onCompanyClick
        ),
        InputField(
            value = "Ruta",
            preIcon = Icons.Default.LocationOn,
            onClick = onRouteClick
        )
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        fields.forEach { field ->
            CommonInputCards(
                preIcon = field.preIcon,
                value = field.value,
                icon = Icons.Default.ArrowDropDown,
                onClick = field.onClick
            )
        }
    }

}

@Composable
private fun Footer(isEnabled: Boolean) {
    CommonButtons(
        title = "Registrar zona de trabajo",
        isEnabled = isEnabled,
        isLoading = false,
        onClick = { }
    )
}

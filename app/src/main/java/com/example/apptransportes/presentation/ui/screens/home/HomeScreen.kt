package com.example.apptransportes.presentation.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (uiState.activeDialog != HomeDialogType.NONE) {
            AlertDialog(
                onDismissRequest = { homeViewModel.dismissDialog() },
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
            ) {
                Surface(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    shape = MaterialTheme.shapes.extraLarge,
                    tonalElevation = AlertDialogDefaults.TonalElevation
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                    ) {
                        Text(
                            text = when (uiState.activeDialog) {
                                HomeDialogType.EMPRESAS -> "Selecciona una Empresa"
                                HomeDialogType.RUTAS -> "Seleccione una Ruta"
                                else -> ""
                            },
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        if (uiState.isLoading) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(20.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator()
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    "Cargando datos...",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        } else {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier
                                    .heightIn(max = 250.dp)
                                    .padding(vertical = 8.dp)
                            ) {
                                when (uiState.activeDialog) {
                                    HomeDialogType.NONE -> {}

                                    HomeDialogType.EMPRESAS -> {
                                        if (uiState.listEmpresas.isEmpty()) {
                                            item {
                                                Text(
                                                    text = uiState.errorMessage ?: "Error",
                                                    modifier = Modifier.padding(12.dp),
                                                    style = MaterialTheme.typography.bodyLarge
                                                )
                                            }
                                        } else {
                                            items(uiState.listEmpresas) { empresa ->
                                                Surface(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(horizontal = 4.dp)
                                                        .background(Color.Transparent),
                                                    shape = MaterialTheme.shapes.medium,
                                                    tonalElevation = 1.dp,
                                                    onClick = {
                                                        homeViewModel.onCompanySelected(empresa)
                                                        homeViewModel.dismissDialog()
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

                                    HomeDialogType.RUTAS -> {
                                        if (uiState.listRutas.isEmpty()) {
                                            item {
                                                Text(
                                                    text = uiState.errorMessage ?: "Error",
                                                    modifier = Modifier.padding(12.dp),
                                                    style = MaterialTheme.typography.bodyLarge
                                                )
                                            }
                                        } else {
                                            items(uiState.listRutas) { ruta ->
                                                Surface(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(horizontal = 4.dp)
                                                        .background(Color.Transparent),
                                                    shape = MaterialTheme.shapes.medium,
                                                    tonalElevation = 1.dp,
                                                    onClick = {
                                                        homeViewModel.onRouteSelected(ruta)
                                                        homeViewModel.dismissDialog()
                                                    }
                                                ) {
                                                    Text(
                                                        text = ruta.nombre,
                                                        modifier = Modifier.padding(12.dp),
                                                        style = MaterialTheme.typography.bodyLarge
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        CommonButtons(
                            title = "Cerrar",
                            isEnabled = true,
                            isLoading = false,
                            onClick = { homeViewModel.dismissDialog() }
                        )
                    }
                }
            }
        }
        Inputs(
            empresa = uiState.empresa?.nombre,
            ruta = uiState.ruta?.nombre,
            onCompanyClick = {
                homeViewModel.showDialog(type = HomeDialogType.EMPRESAS)
            },
            onRouteClick = {
                homeViewModel.showDialog(type = HomeDialogType.RUTAS)
            },
        )
        Footer(isEnabled = uiState.isEnabled)
    }
}


data class InputField(
    val value: String,
    val enabled: Boolean,
    val preIcon: ImageVector,
    val onClick: () -> Unit
)

@Composable
private fun Inputs(
    empresa: String?,
    ruta: String?,
    onCompanyClick: () -> Unit,
    onRouteClick: () -> Unit
) {
    val fields = listOf(
        InputField(
            value = "Fecha",
            enabled = true,
            preIcon = Icons.Default.CalendarToday,
            onClick = {}
        ),
        InputField(
            value = empresa ?: "Empresa",
            enabled = true,
            preIcon = Icons.Default.Business,
            onClick = onCompanyClick
        ),
        InputField(
            value = ruta ?: "Ruta",
            enabled = !empresa.isNullOrEmpty(),
            preIcon = Icons.Default.LocationOn,
            onClick = onRouteClick
        )
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        fields.forEach { field ->
            CommonInputCards(
                preIcon = field.preIcon,
                value = field.value,
                enabled = field.enabled,
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

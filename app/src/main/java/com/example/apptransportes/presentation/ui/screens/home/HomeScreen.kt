package com.example.apptransportes.presentation.ui.screens.home

import android.widget.Toast
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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apptransportes.presentation.ui.components.CommonAlertDialog
import com.example.apptransportes.presentation.ui.components.CommonButtons
import com.example.apptransportes.presentation.ui.components.CommonInputCards

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.successMessage) {
        if(!uiState.successMessage.isNullOrEmpty()){
            Toast.makeText(context,uiState.successMessage,Toast.LENGTH_LONG).show()
            homeViewModel.clearMessages()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        when (uiState.activeDialog) {
            HomeDialogType.EMPRESAS -> {
                CommonAlertDialog(
                    title = "Selecciona una Empresa",
                    items = uiState.listEmpresas,
                    isLoading = uiState.isLoading,
                    errorMessage = uiState.errorMessage,
                    itemLabel = { it.nombre },
                    onItemClick = { empresa ->
                        homeViewModel.onCompanySelected(empresa)
                        homeViewModel.dismissDialog()
                    },
                    onDismiss = { homeViewModel.dismissDialog() }
                )
            }

            HomeDialogType.RUTAS -> {
                CommonAlertDialog(
                    title = "Selecciona una Ruta",
                    items = uiState.listRutas,
                    isLoading = uiState.isLoading,
                    errorMessage = uiState.errorMessage,
                    itemLabel = { it.nombre },
                    onItemClick = { ruta ->
                        homeViewModel.onRouteSelected(ruta)
                        homeViewModel.dismissDialog()
                    },
                    onDismiss = { homeViewModel.dismissDialog() }
                )
            }

            else -> {}
        }
        if (uiState.activeDateDialog) {
            MyDateDialog(uiState = uiState, homeViewModel = homeViewModel)
        }
        Body(
            fecha = homeViewModel.dateText(),
            empresa = uiState.empresa?.nombre,
            ruta = uiState.ruta?.nombre,
            onDateClick = {
                homeViewModel.showDateDialog()
            },
            onCompanyClick = {
                homeViewModel.showDialog(type = HomeDialogType.EMPRESAS)
            },
            onRouteClick = {
                homeViewModel.showDialog(type = HomeDialogType.RUTAS)
            },
        )
        Footer(isEnabled = uiState.isEnabled, isLoading = uiState.isLoading, onRegisterButton = { homeViewModel.onSaveClick() })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyDateDialog(uiState: HomeUiState, homeViewModel: HomeViewModel) {
    val today = System.currentTimeMillis()
    val initDate = uiState.fecha ?: today
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initDate)
    DatePickerDialog(
        onDismissRequest = { homeViewModel.dismissDateDialog() },
        confirmButton = {
            TextButton(onClick = {
                homeViewModel.onDateSelected(date = datePickerState.selectedDateMillis!!)
                homeViewModel.dismissDateDialog()
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = { homeViewModel.dismissDateDialog() }) {
                Text("Cancelar")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            title = { Text(text = "Seleccione una fecha", modifier = Modifier.padding(12.dp)) })
    }
}

@Composable
private fun Body(
    fecha: String?,
    empresa: String?,
    ruta: String?,
    onDateClick: () -> Unit,
    onCompanyClick: () -> Unit,
    onRouteClick: () -> Unit
) {
    val camposConfiguracion = listOf(
        ConfigurationFields(
            value = fecha ?: "Fecha",
            enabled = true,
            preIcon = Icons.Default.CalendarToday,
            onClick = onDateClick
        ),
        ConfigurationFields(
            value = empresa ?: "Empresa",
            enabled = true,
            preIcon = Icons.Default.Business,
            onClick = onCompanyClick
        ),
        ConfigurationFields(
            value = ruta ?: "Ruta",
            enabled = !empresa.isNullOrEmpty(),
            preIcon = Icons.Default.LocationOn,
            onClick = onRouteClick
        )
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        camposConfiguracion.forEach { campos ->
            CommonInputCards(
                preIcon = campos.preIcon,
                value = campos.value,
                enabled = campos.enabled,
                icon = Icons.Default.ArrowDropDown,
                onClick = campos.onClick
            )
        }
    }

}

@Composable
private fun Footer(isEnabled: Boolean, isLoading: Boolean, onRegisterButton: () -> Unit) {
    CommonButtons(
        title = "Registrar zona de trabajo",
        isEnabled = isEnabled,
        isLoading = isLoading,
        onClick = { onRegisterButton() }
    )
}

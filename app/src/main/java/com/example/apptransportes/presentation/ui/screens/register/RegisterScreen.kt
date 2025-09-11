package com.example.apptransportes.presentation.ui.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apptransportes.presentation.ui.components.CommonAlertDialog
import com.example.apptransportes.presentation.ui.components.CommonInputCards
import com.example.apptransportes.presentation.ui.components.CommonOutlinedTextField
import com.example.apptransportes.presentation.ui.theme.RedPastel

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {

    val uiState by registerViewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(vertical = 10.dp)
    ) {

        if (uiState.activeAlertDialog) {
            CommonAlertDialog(
                title = "Seleccione Tipo Registro",
                items = uiState.registerTypes,
                isLoading = uiState.isLoading,
                errorMessage = uiState.errorMessage,
                itemLabel = { it.type },
                onItemClick = {
                    registerViewModel.onTypeSelected(it)
                    registerViewModel.dismissDialog()
                },
                onDismiss = { registerViewModel.dismissDialog() }
            )
        }

        Configurations(
            modifier = Modifier.padding(horizontal = 20.dp),
            dni = uiState.dni,
            selectedType = uiState.selectedRegisterType?.type,
            onTypeConfigurationClick = {
                registerViewModel.getRegisterTypes()
                registerViewModel.showDialog()
            },
            onDniChange = {
                registerViewModel.onDniChange(it)
            }
        )

        SummaryBar(total = 15)

        Spacer(Modifier.height(10.dp))

        Registers(
            modifier = Modifier.padding(horizontal = 20.dp),
            registros = List(15) {
                RegistroUi(
                    dni = "7427853$it",
                    nombre = "Colaborador $it",
                    tipo = if (it % 2 == 0) "Recojo" else "Entrega",
                    sincronizado = it % 3 != 0
                )
            }
        )
    }
}


@Composable
private fun Configurations(
    modifier: Modifier = Modifier,
    dni: String,
    selectedType: String?,
    onTypeConfigurationClick: () -> Unit,
    onDniChange: (String) -> Unit
) {
    var useSystemTime by remember { mutableStateOf(false) }

    Column(modifier = modifier) {

        CommonInputCards(
            preIcon = Icons.Default.AppRegistration,
            value = selectedType ?: "Tipo de Registro",
            enabled = true,
            icon = Icons.Default.ArrowDropDown,
            onClick = onTypeConfigurationClick
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            CommonOutlinedTextField(
                modifier = Modifier.weight(1f),
                value = dni,
                onValueChange = { onDniChange(it) },
                label = "DNI Colaborador",
                icon = Icons.Default.CreditCard
            )
            IconButton(onClick = {}) {
                Icon(Icons.Default.CameraAlt, contentDescription = "Escanear DNI")
            }
        }

        CommonOutlinedTextField(
            value = "",
            onValueChange = {},
            label = "Hora de Registro",
            icon = Icons.Default.AccessTime
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = useSystemTime, onCheckedChange = { useSystemTime = it })
            Text("Usar hora del celular")
        }
    }
}

@Composable
private fun SummaryBar(total: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(RedPastel)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Total de Registros", color = Color.White, fontWeight = FontWeight.SemiBold)
        Text("$total", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

data class RegistroUi(
    val dni: String,
    val nombre: String,
    val tipo: String,
    val sincronizado: Boolean
)

@Composable
private fun Registers(modifier: Modifier, registros: List<RegistroUi>) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 80.dp) // evitar chocar con el FAB
        ) {
            items(registros) { registro ->
                RegistroCard(registro)
            }
        }
    }
}

@Composable
private fun RegistroCard(registro: RegistroUi) {
    Card(
        onClick = {},
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("DNI: ${registro.dni}", fontWeight = FontWeight.SemiBold)
                Text(
                    if (registro.sincronizado) "Sincronizado" else "Pendiente",
                    color = if (registro.sincronizado) Color(0xFF4CAF50) else Color(0xFFF44336),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Text("Colaborador: ${registro.nombre}", fontSize = 14.sp)
            Text("Tipo de Registro: ${registro.tipo}", fontSize = 13.sp, color = Color.Gray)
        }
    }
}

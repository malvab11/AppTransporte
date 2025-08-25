package com.example.apptransportes.presentation.ui.screen.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apptransportes.presentation.ui.components.CommonOutlinedTextField
import com.example.apptransportes.presentation.ui.theme.RedPastel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(padding: PaddingValues, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Personal", fontWeight = FontWeight.Bold) },
                actions = { Icon(Icons.Default.Search, contentDescription = "") }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* acción de sincronizar */ },
                containerColor = RedPastel,
                contentColor = Color.White,
                text = { Text("Sincronizar") },
                icon = { Icon(Icons.Default.Sync, contentDescription = null) }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5))
        ) {
            Inputs(Modifier.padding(horizontal = 20.dp))

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
}

@Composable
private fun Inputs(modifier: Modifier = Modifier) {
    var useSystemTime by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        CommonOutlinedTextField(
            readOnly = true,
            label = "Tipo de Registro",
            value = "",
            onValueChange = {},
            icon = Icons.Default.ArrowDropDown,
            preIcon = Icons.Default.AppRegistration
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            CommonOutlinedTextField(
                modifier = Modifier.weight(1f),
                value = "",
                onValueChange = {},
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

package com.example.apptransportes.presentation.ui.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.apptransportes.BuildConfig
import com.example.apptransportes.R
import com.example.apptransportes.presentation.ui.components.CommonButtons
import com.example.apptransportes.presentation.ui.components.CommonOutlinedTextField

@Composable
fun LoginScreen(padding: PaddingValues,viewModel: LoginViewModel = hiltViewModel(), onLogginSucces: () -> Unit) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = padding)
            .background(Color(0xFFF5F5F5)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.size(32.dp))
        Header()
        Body(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            dni = uiState.dni,
            password = uiState.password,
            errorMessage = uiState.errorMessage,
            isPasswordHide = uiState.isPasswordHide,
            isEnabled = uiState.isEnabled,
            isLoading = uiState.isLoading,
            onDniChange = { viewModel.onUserChange(it) },
            onPasswordChange = { viewModel.onPasswordChange(it) },
            onIconClick = { viewModel.onIconPressed() },
            onLogginPressed = { viewModel.onLogginPressed() }
        )
        Footer()
        Spacer(Modifier.size(16.dp))
    }
}

@Composable
private fun Header() {
    Image(
        painter = painterResource(R.drawable.logo_app),
        contentDescription = "Logo Principal de Empresa",
        modifier = Modifier.size(100.dp)
    )
    Spacer(Modifier.size(10.dp))
    Text(
        text = "Transporte de Personal",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF333333)
    )
    Spacer(Modifier.size(16.dp))
    Text(
        text = "Iniciar Sesión",
        fontSize = 16.sp,
        color = Color.Gray
    )
}

@Composable
private fun Body(
    modifier: Modifier,
    dni: String,
    password: String,
    errorMessage: String?,
    isPasswordHide: Boolean,
    isEnabled: Boolean,
    isLoading: Boolean,
    onDniChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onIconClick: () -> Unit,
    onLogginPressed: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        // Campo Usuario
        CommonOutlinedTextField(
            value = dni,
            onValueChange = onDniChange,
            label = "Usuario",
            errorMessage = errorMessage ?: "",
            icon = Icons.Default.Person,
            isPassword = false,
        )

        Spacer(modifier = Modifier.size(16.dp))

        // Campo Contraseña

        CommonOutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = "Contraseña",
            errorMessage = errorMessage ?: "",
            isPassword = true,
            onIconClick = onIconClick,
            isPasswordHide = isPasswordHide,
            keyboardType = KeyboardType.Password
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = "¿Olvidaste tu contraseña?",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { },
            textAlign = TextAlign.End,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.size(24.dp))

        // Botón principal
        CommonButtons(
            title = "Ingresar",
            isEnabled = isEnabled,
            isLoading = isLoading
        ) { onLogginPressed() }
    }
}

@Composable
private fun Footer() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.size(12.dp))
        Text(
            text = "v. ${BuildConfig.VERSION_NAME}",
            color = Color.Gray,
            fontSize = 12.sp
        )
        Spacer(Modifier.size(12.dp))
    }
}

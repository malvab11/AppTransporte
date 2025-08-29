package com.example.apptransportes.presentation.ui.screens.loading

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apptransportes.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun LoadingScreen(modifier: Modifier = Modifier, onSuccess: () -> Unit) {

    LaunchedEffect(true) {
        delay(5000)
        withContext(Dispatchers.Main) { onSuccess() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        Spacer(Modifier.size(20.dp))
        CircularProgressIndicator()
    }
}
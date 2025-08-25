package com.example.apptransportes.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CommonOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit,
    label: String = "",
    placeholder: String = "",
    errorMessage: String = "",
    isPassword: Boolean = false,
    icon: ImageVector? = null,
    onIconClick: () -> Unit = {},
    isPasswordHide: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text
) {

    val isError = errorMessage.isNotEmpty()

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        singleLine = true,
        isError = isError,
        supportingText = if (isError) {
            {
                Text(text = errorMessage)
            }
        } else {
            null
        },
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = onIconClick) {
                    Icon(
                        imageVector = if (isPasswordHide) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            }
        } else {
            {
                Icon(imageVector = icon!!, contentDescription = null)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if(isPassword && isPasswordHide) PasswordVisualTransformation() else VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            unfocusedBorderColor = Color.LightGray
        ),
        shape = RoundedCornerShape(16.dp)
    )

}
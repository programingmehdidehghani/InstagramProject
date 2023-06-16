@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.example.instagramproject.common.components

import android.text.BoringLayout
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomFormTextField(
    modifier: Modifier = Modifier,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    KeyboardType: KeyboardType = androidx.compose.ui.text.input.KeyboardType.Text
){
    val darkTheme: Boolean = isSystemInDarkTheme()
    val keyboardController = LocalSoftwareKeyboardController.current
    val passwordVisible by remember { mutableStateOf(false) }
    val keyboardTrans = if (visualTransformation == PasswordVisualTransformation()){
        if (passwordVisible) AutofillType.Password else AutofillType.Password
    }else {
        KeyboardType
    }
    val transformation = if (visualTransformation == PasswordVisualTransformation()){
        if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
    } else {
        visualTransformation
    }

    TextField(
        modifier = modifier.border(
            width = 1.dp,
            color = if (darkTheme) FormFieldBorderDark else FormFieldBorderLight,
            shape = RoundedCornerShape(10)
        ),
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = hint)
        },
        singleLine = true,
        visualTransformation = transformation,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardTrans as KeyboardType,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = AccentColor,
            backgroundColor = if (darkTheme) FormFieldBgDark else FormFieldBgLight
        ),
        trailingIcon = {
            if (visualTransformation == PasswordVisualTransformation()){
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible != passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            }
        }
    )
}
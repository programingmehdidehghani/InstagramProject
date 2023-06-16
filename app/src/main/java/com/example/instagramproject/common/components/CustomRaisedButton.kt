package com.example.instagramproject.common.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomRaisedButton(
    modifier: Modifier = Modifier,
    text: String,
    isLoading: Boolean = false,
    onClick: () -> Unit
){
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = AccentColor),
        shape = RoundedCornerShape(size = 5.dp),
        onClick = onClick
    ) {
        if (isLoading){
            CircularProgressIndicator(color = IconDark)
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.button,
                color = Color.White,
                modifier = Modifier.padding(vertical = 7.dp)
            )
        }
        
    }

}
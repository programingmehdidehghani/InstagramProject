package com.example.instagramproject.auth.presentation.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun Activity(){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row (modifier = Modifier.padding(start = 10.dp, end = 13.dp , top = 10.dp, bottom = 20.dp)){
             Text(text = "Notifications", fontWeight = FontWeight.Bold, fontSize = 26.sp)
        }
        LazyColumn{
            items(1){

            }
        }
    }
}

@Composable
fun MyUi(modifier: Modifier = Modifier.padding(10.dp)){

    (0..20).forEach {index ->
        Row(
            modifier = Modifier.fillMaxWidth().padding(5.dp)
        ) {

        }
    }
}
package com.example.instagramproject.auth.presentation.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.instagramproject.R
import com.example.instagramproject.auth.model.names

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth(1.0f)) {

                    Image(
                        painter = rememberAsyncImagePainter("https://randomuser.me/api/portraits/men/${index + 1}.jpg"),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(50.dp)
                            .height(40.dp)
                            .clip(CircleShape)
                    )
            }
            Column(modifier = Modifier.fillMaxWidth(0.85f)) {
                Text(modifier = Modifier
                    .padding(10.dp),
                     text = buildAnnotatedString {
                         withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                             append(names[index])
                         }
                         withStyle(style = SpanStyle(fontWeight = FontWeight.Normal, fontSize = 13.sp)){
                             append(""+ stringResource(id = R.string.acitivitystring))
                         }
                     }

                )
            }
            Column(modifier = Modifier.fillMaxWidth()) {

                Image(
                    painter = rememberAsyncImagePainter("https://source.unsplash.com/random/400x300?$index"),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                )

            }

        }
    }
}
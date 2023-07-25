package com.example.instagramproject.auth.presentation.login

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.instagramproject.R
import com.example.instagramproject.auth.presentation.AuthScreenEvents
import com.example.instagramproject.auth.presentation.AuthViewModel
import com.example.instagramproject.auth.presentation.ResultEvents
import com.example.instagramproject.common.components.CustomFormTextField
import com.example.instagramproject.common.components.CustomRaisedButton
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: AuthViewModel = hiltViewModel()
){
    val scaffoldState = rememberScaffoldState()
    
    LaunchedEffect(key1 = true){
        viewModel.eventChannelFlow.collectLatest { events: ResultEvents ->
            when(events){
                is ResultEvents.OnError -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = events.message,
                        duration = SnackbarDuration.Short
                    )
                }
                is  ResultEvents.OnSuccess -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Sign in Successful",
                        duration = SnackbarDuration.Short
                        )
                    navigator.navigate(MainScre)
                }
                else -> {}
            }
        }
    }

    androidx.compose.material.Scaffold(scaffoldState = scaffoldState) {
        Box (
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
          ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                Image(painter = painterResource(id = R.drawable.ic_instagram_logo),
                    contentDescription = null )

                Spacer(modifier = Modifier.height(40.dp))

            }

        }

    }


}

@Composable
fun SignUpSection(onSignUpClicked: () -> Unit){
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .height(1.dp)
                .weight(1f)
                .background(LineColor)
        )
        Text(
            text = "OR",
            style = MaterialTheme.typography.body1,
            fontSize = 12.sp,
            color = LightGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier
                .height(1.dp)
                .weight(1f)
                .background(LineColor)
        )
    }

    Spacer(modifier = Modifier.height(40.dp))

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp)
    ) {
        Text(
            text = "Don't have an account?",
            style = MaterialTheme.typography.button,
            color = LightGray,
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "Sign up",
            style = MaterialTheme.typography.button,
            modifier = Modifier.clickable { onSignUpClicked() }
        )
    }

    }

}

@Composable
fun ColumnScope.FormSection(viewModel: AuthViewModel){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    CustomFormTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        hint = "Email",
        value = email,
        KeyboardType = KeyboardType.Email,
        onValueChange = { email = it },
        )

    Spacer(modifier = Modifier.height(12.dp))


    Text(
        text = "Forgot password?",
        style = MaterialTheme.typography.body1,
        fontSize = 12.sp,
        color = AccentColor,
        textAlign = TextAlign.End,
        modifier = Modifier
            .padding(end = 16.dp)
            .align(Alignment.End)
            .clickable { }
    )
    Spacer(modifier = Modifier.height(30.dp))

    CustomRaisedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        text = "Log in",
        isLoading = viewModel.isLoading
        ) {
        viewModel.onUserEvents(
            AuthScreenEvents.OnLogin(
                email = email.trim(),
                password = password.trim()
            )
        )
        
    }
    Spacer(modifier = Modifier.height(38.dp))

    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        Icon(
            imageVector = painterResource(id = R.drawable.ic_facebook),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Log in with Facebook",
            style = MaterialTheme.typography.body1,
            color = AccentColor
        )

    }

}
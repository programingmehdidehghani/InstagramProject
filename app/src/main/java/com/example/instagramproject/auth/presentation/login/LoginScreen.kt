package com.example.instagramproject.auth.presentation.login

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.instagramproject.R
import com.example.instagramproject.auth.presentation.AuthViewModel
import com.example.instagramproject.auth.presentation.ResultEvents
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

            }

        }

    }


}
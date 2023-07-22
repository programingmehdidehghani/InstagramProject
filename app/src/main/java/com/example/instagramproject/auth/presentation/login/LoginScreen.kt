package com.example.instagramproject.auth.presentation.login

import androidx.compose.foundation.layout.Box
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.example.instagramproject.auth.presentation.AuthViewModel
import com.example.instagramproject.auth.presentation.ResultEvents
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest


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
            
                ){

        }

    }


}
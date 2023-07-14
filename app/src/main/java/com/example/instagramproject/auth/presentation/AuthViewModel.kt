package com.example.instagramproject.auth.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagramproject.auth.domain.AuthRepository
import com.example.instagramproject.auth.domain.AuthValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val eventChannel = Channel<ResultEvents>()
    val eventChannelFlow = eventChannel.receiveAsFlow()

    var isLoading by mutableStateOf(false)
        private set

    fun onUserEvents(authScreenEvents: AuthScreenEvents){
        when(authScreenEvents){
            is AuthScreenEvents.OnLogin -> {
                val email = authScreenEvents.email
                val password = authScreenEvents.password

                val result = AuthValidator.validateSignInRequest(email,password)
                if (result.successful){
                    sig
                }
            }
        }

    }

    fun signIn(email: String, password: String) = viewModelScope.launch{
        isLoading = true
        try {
            val firebaseUser = authRepository.signInWithEmailAndPassword(email,password)
            isLoading = false

            firebaseUser?.let { user ->
                if (user.isEmailVerified){
                    eventChannel.send(ResultEvents.OnSuccess("Login successful"))
                } else {
                    eventChannel.send(ResultEvents.OnError("User email is not verified. Kindly verify to login"))
                }

            }

        } catch (e: Exception){
            isLoading = false
            eventChannel.send(ResultEvents.OnError(e.localizedMessage ?: "Unable to Login User, try again."))

        }
    }
}
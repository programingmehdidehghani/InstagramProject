package com.example.instagramproject.auth.presentation

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagramproject.auth.data.dto.CreateUserDto
import com.example.instagramproject.auth.domain.AuthRepository
import com.example.instagramproject.auth.domain.AuthValidator
import com.google.firebase.firestore.FieldValue
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
                    signIn(email,password)
                } else {
                    viewModelScope.launch {
                        eventChannel.send(ResultEvents.OnError(result.error!!))
                    }
                }
            }
            is AuthScreenEvents.OnRegister -> {
                if (authScreenEvents.imageUri == null){
                    viewModelScope.launch {
                        eventChannel.send(ResultEvents.OnError("You have not selected an image"))
                    }
                    return
                }
                val result = AuthValidator.validateCreateUserRequest(authScreenEvents.createUserDto)
                if (result.successful){
                    createUser(authScreenEvents.imageUri,authScreenEvents.createUserDto)
                } else {
                    viewModelScope.launch {
                        eventChannel.send(ResultEvents.OnError(result.error!!))
                    }
                }
            }
        }

    }


    fun createUser(imageUri: Uri, createUserDto: CreateUserDto) = viewModelScope.launch {
         isLoading = true
        try {
            val usernameAvailability = authRepository.checkUsernameAvailability(createUserDto.username)
            if (!usernameAvailability){
                isLoading = false
                eventChannel.send(ResultEvents.OnError("Username entered already exist, try another one."))
            } else {
                val firebaseUser = authRepository.createUserWithEmailAndPassword(
                    createUserDto.email,
                    createUserDto.password
                )
                firebaseUser?.let { user ->
                    val imageUri = authRepository.uploadProfileImage(imageUri)

                    val updateUser = createUserDto.copy(
                        uid = user.uid,
                        createdDate = FieldValue.serverTimestamp(),
                        imageUrl = imageUri,
                        password = ""
                    )
                    authRepository.saveUserProfile(updateUser)

                }
                isLoading = false
                eventChannel.send(ResultEvents.OnSuccess("User created, kindly verify your mail to login"))
            }

        }catch (e: Exception){
            isLoading = false
            eventChannel.send(
                ResultEvents.OnError(
                    e.localizedMessage ?: "Unable to Create User, try again."
                )
            )
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
package com.example.instagramproject.common.components

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput


@Composable
fun DoubleTapPhotoLikeAnimation(onDoubleTap: () -> Unit){

    var transitionState by remember {
        mutableStateOf(MutableTransitionState(LikedStates.Disappeared))
    }
    
    Box(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        transitionState = MutableTransitionState(LikedStates.Initial)
                        onDoubleTap.invoke()
                    }
                )
            }
    ){
        if (transitionState.currentState == LikedStates.Initial){
            transitionState.targetState = LikedStates.Liked
        } else if (transitionState.currentState == LikedStates.Liked){
            transitionState.targetState = LikedStates.Disappeared
        }

        val transition = updateTransition(transitionState,label = "")

        val alpha by transition.animateFloat(
            transitionSpec = {
                when {
                    LikedStates.Initial isTransitioningTo LikedStates.Liked ->
                        keyframes {
                            durationMillis = 500
                            0f at 0
                            0.5f at 100
                            1f at 225
                        }
                    LikedStates.Liked isTransitioningTo LikedStates.Disappeared ->
                        tween(delayMillis = 500)
                    else -> snap()
                }
            }, label = ""
        ) {
           if (it == LikedStates.Liked) 1f else 0f
        }

        val scale by transition.animateFloat(
            transitionSpec = {
                when {
                    LikedStates.Initial isTransitioningTo 
                }
            }
        ) {

        }
    }

}


enum class LikedStates {
    Initial,
    Liked,
    Disappeared
}
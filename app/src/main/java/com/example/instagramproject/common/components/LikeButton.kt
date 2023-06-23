package com.example.instagramproject.common.components

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.instagramproject.R
import com.example.instagramproject.auth.model.Post

enum class LikeAnimationState{
    Initial,
    Start,
    End
}

private const val springRatio = Spring.DampingRatioHighBouncy

@Composable
fun AnimLikeButton(
    post: Post,
    onLikeClick: (Post) -> Unit
){
    var transitionState by remember {
        mutableStateOf(MutableTransitionState(LikeAnimationState.Initial))
    }

    Box(
      modifier = Modifier
          .clickable(
              onClick = {
                  transitionState = MutableTransitionState(LikeAnimationState.Start)
                  onLikeClick.invoke(post)
              }
          )
          .indication(
              indication = rememberRipple(bounded = false, radius = 24.dp),
              interactionSource = remember { MutableInteractionSource() }
          )
          .padding(vertical = 10.dp)
          .then(Modifier.size(30.dp)),
        contentAlignment = Alignment.Center
    ) {

        val likeIconRes by remember { mutableStateOf(R.drawable.ic_outlined_favorite) }

    }

}
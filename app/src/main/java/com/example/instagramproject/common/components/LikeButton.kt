package com.example.instagramproject.common.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.instagramproject.R
import com.example.instagramproject.auth.model.Post
import com.example.instagramproject.auth.model.User
import com.example.instagramproject.auth.model.names

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

        var likeIconRes by remember { mutableStateOf(R.drawable.ic_outlined_favorite) }
        val startColor = contentColorFor(MaterialTheme.colorScheme.background)
        val endColor = Color(0xFFDF0707)

        if (transitionState.currentState == LikeAnimationState.Start){
            transitionState.targetState = LikeAnimationState.End
        }

        val transition = updateTransition(transitionState,label = "")

        val animatedColor by transition.animateColor (
              transitionSpec = {
                  when {
                      LikeAnimationState.Initial isTransitioningTo LikeAnimationState.Start ->
                          spring(dampingRatio = springRatio)
                      LikeAnimationState.Start isTransitioningTo LikeAnimationState.End ->
                          tween(200)
                      else -> snap()
                  }
              }, label = ""
           ){ state ->
           when(state){
               LikeAnimationState.Initial -> if (post.isLiked) endColor else startColor
               else -> if (post.isLiked.not()) startColor else endColor
           }

        }

        val size by transition.animateDp(
            transitionSpec = {
                when{
                    LikeAnimationState.Initial isTransitioningTo LikeAnimationState.Start ->
                        spring(dampingRatio = springRatio)
                    LikeAnimationState.Start isTransitioningTo LikeAnimationState.End ->
                        tween(200)
                    else -> snap()
                }
            }, label = ""
        ) { state ->
         when(state){
             LikeAnimationState.Initial -> 24.dp
             LikeAnimationState.Start -> 12.dp
             LikeAnimationState.End -> 24.dp
         }
            
        }

        likeIconRes = if (post.isLiked){
            R.drawable.ic_filled_favorite
        } else {
            R.drawable.ic_outlined_favorite
        }
        
        Icon(
            ImageBitmap.imageResource(id = likeIconRes), tint = animatedColor,
            modifier = Modifier.size(size),
            contentDescription = ""
        )

    }

}

@Preview
@Composable
fun LikeButtonPreview(){
    AnimLikeButton(
        post = Post(
            id = 1,
            image = "https://source.unsplash.com/random/400x300",
            user = User(
                name = names.first(),
                username = names.first(),
                image = "https://randomuser.me/api/portraits/men/1.jpg"
            ),
            likesCount = 100,
            commentsCount = 20,
            timeStamp = System.currentTimeMillis() - (6000)
        ),
        onLikeClick = {
            
        }
    )
}
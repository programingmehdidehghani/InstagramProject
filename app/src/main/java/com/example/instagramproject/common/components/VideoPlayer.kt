package com.example.instagramproject.common.components

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util


@SuppressLint("RememberReturnType")
@Composable
fun VideoPlayer(uri: Uri){
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
               val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                   context,Util.getUserAgent(context,context.packageName)

               )
                val source = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                    MediaItem.fromUri(uri))

                this.prepare(source)
            }
    }

    exoPlayer.playWhenReady = true
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
}
package com.example.instagramproject.auth.data

import com.example.instagramproject.auth.model.Reel
import com.example.instagramproject.auth.model.User
import com.example.instagramproject.auth.model.names

object ReelsRepository {

    private val reels = arrayListOf<Reel>()

    private fun populateReels(){
        val _reels = ArrayList<Reel>()
        (0..9).forEach{ index ->
            val post = Reel(
                id = index + 1,
                video = videos[index],
                user = User(
                    name = names[index],
                    username = names[index],
                    image = "https://randomuser.me /api/portraits/men/${index + 1}.jpg"
                ),
                likesCount = index + 100,
                commentsCount = index + 20
            )
            _reels.add(post)
        }
        reels.clear()
        reels.addAll(_reels)
    }

    private val videos = listOf(
        "food.mp4",
        "soap-bubbles.mp4",
        "castle.mp4",
        "lake.mp4",
        "icecream.mp4",
        "soap-bubbles.mp4",
        "castle.mp4",
        "lake.mp4",
        "icecream.mp4",
        "soap-bubbles.mp4",
        "castle.mp4",
        "lake.mp4",
        "icecream.mp4",
    )
}
package com.example.instagramproject.auth.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.instagramproject.auth.model.Post
import com.example.instagramproject.auth.model.Story
import com.example.instagramproject.auth.model.currentUser
import com.example.instagramproject.auth.model.names

object StoriesRepository {

    private val stories = mutableStateOf<List<Story>>(emptyList())

    private fun populateStories(){
        val _stories = ArrayList<List<Story>>()

        _stories.add(
            listOf(
                Story(
                    image = currentUser.image,
                    name = "Your Story"
                )
            )
        )

        (0..9).forEach { index ->
             val story = Story(
                 image = "https://randomuser.me/api/portraits/men/${index + 1}.jpg",
                 name = names[index]
               )
            _stories.add(listOf(story))
        }
        stories.value = _stories
    }

    init {
        populateStories()
    }

    fun observeStories() : MutableState<List<Story>> = stories
}
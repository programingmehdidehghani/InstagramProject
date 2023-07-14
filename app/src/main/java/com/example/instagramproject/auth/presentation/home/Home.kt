package com.example.instagramproject.auth.presentation.home

import android.annotation.SuppressLint
import android.net.wifi.hotspot2.pps.HomeSp
import android.widget.ExpandableListView.OnChildClickListener
import android.widget.ImageButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.instagramproject.R
import com.example.instagramproject.auth.data.PostsRepository
import com.example.instagramproject.auth.data.StoriesRepository
import com.example.instagramproject.auth.model.Post
import com.example.instagramproject.auth.model.Story
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home() {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { Toolbar() }) {
        val posts by PostsRepository.posts
        val stories by StoriesRepository.observeStories()
        
        LazyColumn {
            item {
                StoriesSection(stories)
                Divider()
            }
            itemsIndexed(posts){ _, post ->
                com.example.instagramproject.auth.presentation.home.Post(
                    post,
                    onDoubleClick = {
                       coroutineScope.launch {
                           PostsRepository.performLike(post.id)
                       }
                    },
                    onLikeToggle = {
                        coroutineScope.launch {
                            PostsRepository.toggleLike(post.id)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun Toolbar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.padding(6.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                ImageVector.vectorResource(id = R.drawable.ic_instagram),
                contentDescription = ""
            )
        }
        Icon(
            ImageBitmap.imageResource(id = R.drawable.ic_dm),
            modifier = Modifier.icon(),
            contentDescription = ""
        )

    }
}

@Composable
fun StoriesSection(stories: List<Story>) {
    Column {
       storiesList(stories)
        Spacer(modifier = Modifier.height(10.dp))
    }

}

@Composable
fun storiesList(stories: List<Story>) {
    LazyRow {
        itemsIndexed(stories) { index, story ->

            if (index == 0) {
                Spacer(modifier = Modifier.width(6.dp))
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 6.dp)
            ) {
                StoryImage(imageUrl = story.image)
                Spacer(modifier = Modifier.height(5.dp))
                Text(story.name, style = MaterialTheme.typography.labelMedium)
            }
            if (index == stories.size.minus(1)){
                Spacer(modifier = Modifier.width(6.dp))
            }

        }
    }
}

@Composable
fun Post(
    post: Post,
    onDoubleClick: (Post) -> Unit,
    onLikeToggle: (Post) -> Unit
){
    PostView(post, onDoubleClick, onLikeToggle)
}
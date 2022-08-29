package com.example.socialdolphin.features.timeline.domain.entities

import com.example.socialdolphin.features.timeline.data.models.PostsTypeCountModel

data class PostsTypeCountEntity(
    val posts: Int,
    val reposts: Int,
    val quotes: Int
) {

    companion object {
        fun mapper(model: PostsTypeCountModel?) = model?.let {
            PostsTypeCountEntity(
                posts = it.posts,
                reposts = it.reposts,
                quotes = it.quotes
            )
        }
    }

}
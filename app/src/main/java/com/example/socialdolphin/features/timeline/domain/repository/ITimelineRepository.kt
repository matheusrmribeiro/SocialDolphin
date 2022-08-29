package com.example.socialdolphin.features.timeline.domain.repository

import com.example.socialdolphin.core.utils.ViewState
import com.example.socialdolphin.features.timeline.domain.entities.PostEntity
import com.example.socialdolphin.features.timeline.domain.entities.PostsTypeCountEntity
import kotlinx.coroutines.flow.Flow

interface ITimelineRepository {

    /**
     * Create a new post.
     * @param entity The post data.
     *
     */
    fun createPost(entity: PostEntity): Flow<ViewState<Int>>

    /**
     * Repost creating a new post.
     * @param entity The post data.
     *
     */
    fun createRepost(entity: PostEntity): Flow<ViewState<Int>>

    /**
     * Get all timeline's post.
     * @param userId The current authenticated user id.
     *
     * @return A list of [PostEntity]
     */
    fun getTimeline(): Flow<List<PostEntity>>

    /**
     * Get all posts related to the user.
     * @param userId The required user id.
     *
     * @return A list of [PostEntity] with the user's posts.
     */
    fun getPostsByUser(userId: String): Flow<List<PostEntity>>

    /**
     * Count all the user's posts by type.
     * @param userId The required user id.
     *
     * @return A class [PostsTypeCountEntity] with the total posts count divided by type
     */
    fun countPostsTypeByUser(userId: String): Flow<PostsTypeCountEntity?>

}
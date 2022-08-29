package com.example.socialdolphin.features.timeline.data.datasource.local

import com.example.socialdolphin.features.timeline.data.models.PostRealmObject
import com.example.socialdolphin.features.timeline.data.models.PostsTypeCountModel

interface ITimelineLocalDataSource {

    /**
     * Create a new post.
     * @param entity The post data.
     *
     */
    fun createPost(model: PostRealmObject)

    /**
     * Get all timeline's post from local data source.
     *
     * @return A list of [PostRealmObject]
     */
    fun getTimeline(): List<PostRealmObject>

    /**
     * Get all posts related to the user.
     * @param userId The required user id.
     *
     * @return A list of [PostRealmObject] with the user's posts.
     */
    fun getPostsByUser(userId: String): List<PostRealmObject>

    /**
     * Count all the user's posts by type.
     * @param userId The required user id.
     *
     * @return A class [PostsTypeCountModel] with the total posts count divided by type
     */
    fun countPostsTypeByUser(userId: String): PostsTypeCountModel?

    /**
     * Count all the user's posts by day.
     * @param userId The required user id.
     * @param date The current date.
     *
     * @return The total posts count
     */
    fun countPostsADayByUser(userId: String, date: String): Int?

}
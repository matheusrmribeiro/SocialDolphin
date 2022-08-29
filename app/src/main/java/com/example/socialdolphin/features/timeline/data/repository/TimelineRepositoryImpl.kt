package com.example.socialdolphin.features.timeline.data.repository

import com.example.socialdolphin.R
import com.example.socialdolphin.core.utils.DateUtils
import com.example.socialdolphin.core.utils.ViewState
import com.example.socialdolphin.features.main.MainActivity
import com.example.socialdolphin.features.timeline.data.datasource.local.ITimelineLocalDataSource
import com.example.socialdolphin.features.timeline.data.models.PostRealmObject
import com.example.socialdolphin.features.timeline.domain.entities.PostEntity
import com.example.socialdolphin.features.timeline.domain.entities.PostTypeEnum
import com.example.socialdolphin.features.timeline.domain.entities.PostsTypeCountEntity
import com.example.socialdolphin.features.timeline.domain.repository.ITimelineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TimelineRepositoryImpl @Inject constructor(
    private val timelineLocalDataSource: ITimelineLocalDataSource
) : ITimelineRepository {
    override fun createPost(entity: PostEntity) = flow {
        val totalPosts =
            timelineLocalDataSource.countPostsADayByUser(entity.creator!!.id, entity.createdAt) ?: 0

        if (totalPosts >= 5) {
            emit(ViewState.Error("", messageRes = R.string.newpost_create_error))
        } else {
            timelineLocalDataSource.createPost(PostRealmObject.mapper(entity))
            emit(ViewState.Success(R.string.newpost_create_success))
        }
    }

    override fun createRepost(entity: PostEntity): Flow<ViewState<Int>> {
        val repost = PostEntity(
            id = DateUtils.generateId(),
            creator = PostEntity.PostCreator(
                id = MainActivity.currentUser.id,
                name = MainActivity.currentUser.name
            ),
            content = entity.content,
            type = PostTypeEnum.REPOST,
            createdAt = DateUtils.getDate(),
            origin = PostEntity.Origin(
                id = entity.id,
                content = entity.content,
                creator = entity.creator
            )
        )
        return createPost(repost)
    }

    override fun getTimeline(): Flow<List<PostEntity>> = flow {
        val realmPosts = timelineLocalDataSource.getTimeline()
        emit(PostEntity.mapper(realmPosts))
    }

    override fun getPostsByUser(userId: String): Flow<List<PostEntity>> = flow {
        val realmPosts = timelineLocalDataSource.getPostsByUser(userId = userId)
        emit(PostEntity.mapper(realmPosts))
    }

    override fun countPostsTypeByUser(userId: String): Flow<PostsTypeCountEntity?> = flow {
        val realmPosts = timelineLocalDataSource.countPostsTypeByUser(userId = userId)
        emit(PostsTypeCountEntity.mapper(realmPosts))
    }

}
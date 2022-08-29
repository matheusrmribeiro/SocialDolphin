package com.example.socialdolphin.features.profile.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.socialdolphin.core.utils.ViewState
import com.example.socialdolphin.features.main.MainActivity
import com.example.socialdolphin.features.profile.domain.entities.UserEntity
import com.example.socialdolphin.features.profile.domain.repository.IUserRepository
import com.example.socialdolphin.features.timeline.domain.entities.PostEntity
import com.example.socialdolphin.features.timeline.domain.entities.PostsTypeCountEntity
import com.example.socialdolphin.features.timeline.domain.repository.ITimelineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: IUserRepository,
    private val repositoryTimeline: ITimelineRepository,
) : ViewModel() {

    var timeline: MutableList<PostEntity> = mutableListOf()
    var statistics: PostsTypeCountEntity = PostsTypeCountEntity(0, 0, 0)
    var user: UserEntity? = null

    fun getUserById(): LiveData<ViewState<UserEntity?>> =
        repository.getUserById(MainActivity.currentUser.id).map {
            user = it
            ViewState.Success(it)
        }.onStart { ViewState.Loading }.asLiveData()

    fun getUserTimelineData(): LiveData<ViewState<List<PostEntity?>>> =
        repositoryTimeline.getPostsByUser(MainActivity.currentUser.id).map {
            timeline = it.toMutableList()
            ViewState.Success(it)
        }.onStart { ViewState.Loading }.asLiveData()

    fun getUserCountData(): LiveData<ViewState<PostsTypeCountEntity?>> =
        repositoryTimeline.countPostsTypeByUser(MainActivity.currentUser.id).map {
            statistics = it ?: PostsTypeCountEntity(0, 0, 0)
            ViewState.Success(it)
        }.onStart { ViewState.Loading }.asLiveData()

    fun createRepost(entity: PostEntity): LiveData<ViewState<Int>> =
        repositoryTimeline.createRepost(entity).asLiveData()
}
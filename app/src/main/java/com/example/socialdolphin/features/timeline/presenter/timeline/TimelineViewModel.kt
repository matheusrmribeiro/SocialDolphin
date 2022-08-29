package com.example.socialdolphin.features.timeline.presenter.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.socialdolphin.core.utils.ViewState
import com.example.socialdolphin.features.timeline.domain.entities.PostEntity
import com.example.socialdolphin.features.timeline.domain.repository.ITimelineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel @Inject constructor(
    private val repository: ITimelineRepository
) : ViewModel() {

    var timeline: MutableList<PostEntity> = mutableListOf()

    fun getTimeline(): LiveData<ViewState<List<PostEntity>>> = repository.getTimeline().map {
        timeline = it.toMutableList()
        ViewState.Success(it)
    }.onStart { ViewState.Loading }.asLiveData()

    fun createRepost(entity: PostEntity): LiveData<ViewState<Int>> =
        repository.createRepost(entity).asLiveData()

}
package com.example.socialdolphin.features.timeline.presenter.newpost

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.socialdolphin.core.utils.DateUtils
import com.example.socialdolphin.core.utils.ViewState
import com.example.socialdolphin.features.main.MainActivity
import com.example.socialdolphin.features.timeline.domain.entities.PostEntity
import com.example.socialdolphin.features.timeline.domain.entities.PostTypeEnum
import com.example.socialdolphin.features.timeline.domain.repository.ITimelineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewPostViewModel @Inject constructor(
    private val repository: ITimelineRepository
) : ViewModel() {

    var originPost: PostEntity? = null

    fun createPost(content: String): LiveData<ViewState<Int>> {
        val origin = originPost?.let {
            return@let PostEntity.Origin(
                id = it.id,
                content = it.content,
                creator = it.creator
            )
        }

        val entity = PostEntity(
            id = DateUtils.generateId(),
            creator = PostEntity.PostCreator(
                id = MainActivity.currentUser.id,
                name = MainActivity.currentUser.name
            ),
            content = content,
            type = if (origin == null) PostTypeEnum.POST else PostTypeEnum.QUOTE,
            active = true,
            createdAt = DateUtils.getDate(),
            origin = origin
        )
        return repository.createPost(entity).asLiveData()
    }

}
package com.example.socialdolphin.features.timeline.domain.entities

import android.os.Parcelable
import com.example.socialdolphin.features.timeline.data.models.CreatorRealmObject
import com.example.socialdolphin.features.timeline.data.models.OriginRealmObject
import com.example.socialdolphin.features.timeline.data.models.PostRealmObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostEntity(
    var id: String = "",
    var creator: PostCreator?,
    var content: String = "",
    var type: PostTypeEnum = PostTypeEnum.POST,
    var active: Boolean = true,
    var createdAt: String = "",
    var origin: Origin? = null
) : Parcelable {

    @Parcelize
    data class PostCreator(
        val id: String,
        val name: String
    ) : Parcelable

    @Parcelize
    data class Origin(
        val id: String,
        val content: String,
        val creator: PostCreator?
    ) : Parcelable

    companion object {

        fun mapper(localObject: PostRealmObject) = PostEntity(
            id = localObject.id,
            creator = mapperCreator(localObject.creator),
            content = localObject.content,
            origin = mapperOrigin(localObject.origin),
            active = localObject.active,
            createdAt = localObject.createdAt,
            type = PostTypeEnum.valueOf(localObject.type.uppercase()),
        )

        fun mapper(localObjects: List<PostRealmObject>) = localObjects.map { mapper(it) }

        fun mapperCreator(localObject: CreatorRealmObject?) = localObject?.let {
            PostCreator(
                id = it.id,
                name = it.name
            )
        }

        fun mapperOrigin(localObject: OriginRealmObject?) = localObject?.let {
            return@let Origin(
                id = it.id,
                content = it.content,
                creator = mapperCreator(it.creator)
            )
        }
    }
}
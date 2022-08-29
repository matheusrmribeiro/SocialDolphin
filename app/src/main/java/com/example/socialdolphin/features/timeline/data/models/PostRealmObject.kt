package com.example.socialdolphin.features.timeline.data.models

import com.example.socialdolphin.features.timeline.domain.entities.PostEntity
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class PostRealmObject(
    var id: String = "",
    var content: String = "",
    var type: String = "",
    var creator: CreatorRealmObject? = null,
    var active: Boolean = true,
    var createdAt: String = "",
    var origin: OriginRealmObject? = null,
) : RealmObject() {

    @PrimaryKey
    var compoundKey = id

    companion object {

        fun mapper(entity: PostEntity) = PostRealmObject(
            id = entity.id,
            creator = CreatorRealmObject.mapper(entity.creator),
            content = entity.content,
            origin = OriginRealmObject.mapper(entity.origin),
            active = entity.active,
            createdAt = entity.createdAt,
            type = entity.type.name.lowercase(),
        )

    }
}
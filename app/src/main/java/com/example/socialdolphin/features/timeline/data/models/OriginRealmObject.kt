package com.example.socialdolphin.features.timeline.data.models

import com.example.socialdolphin.features.timeline.domain.entities.PostEntity
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class OriginRealmObject(
    var id: String = "",
    var content: String = "",
    var creator: CreatorRealmObject? = null,
)  : RealmObject() {

    @PrimaryKey
    var compoundKey = id


    companion object {

        fun mapper(entity: PostEntity.Origin?) = entity?.let {
            return@let OriginRealmObject(
                id = entity.id,
                content = entity.content,
                creator = CreatorRealmObject.mapper(entity.creator)
            )
        }

    }

}
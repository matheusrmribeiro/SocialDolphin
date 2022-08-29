package com.example.socialdolphin.features.timeline.data.models

import com.example.socialdolphin.features.timeline.domain.entities.PostEntity
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CreatorRealmObject(
    var id: String = "",
    var name: String = ""
) : RealmObject() {

    @PrimaryKey
    var compoundKey = id

    companion object {

        fun mapper(entity: PostEntity.PostCreator?) = entity?.let {
            return@let CreatorRealmObject(
                id = it.id,
                name = it.name
            )
        }

    }

}
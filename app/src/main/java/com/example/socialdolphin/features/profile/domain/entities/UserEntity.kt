package com.example.socialdolphin.features.profile.domain.entities

import android.os.Parcelable
import com.example.socialdolphin.features.profile.data.models.UserRealmObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    var id: String = "",
    var name: String = "",
    var socialName: String = "",
    var createdAt: String = "",
) : Parcelable {

    companion object {

        fun mapper(localData: UserRealmObject?) = localData?.let {
            UserEntity(
                id = it.id,
                name = it.name,
                socialName = it.socialName,
                createdAt = it.createdAt,
            )
        }
    }
}
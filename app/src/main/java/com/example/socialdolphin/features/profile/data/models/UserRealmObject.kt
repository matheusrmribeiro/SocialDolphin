package com.example.socialdolphin.features.profile.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class UserRealmObject(
    var id: String = "",
    var name: String = "",
    var socialName: String = "",
    var createdAt: String = "",
) : RealmObject() {

    @PrimaryKey
    var compoundKey = id

}
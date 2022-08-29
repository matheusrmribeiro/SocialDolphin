package com.example.socialdolphin.features.profile.data.datasource.local

import com.example.socialdolphin.features.profile.data.models.UserRealmObject

interface IUserLocalDataSource {

    /**
     * Get the user by the given id
     * @param userId The wanted user id.
     *
     * @return A single [UserRealmObject] by the given id.
     */
    fun getUserById(userId: String): UserRealmObject?

}
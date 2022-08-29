package com.example.socialdolphin.features.profile.domain.repository

import com.example.socialdolphin.features.profile.domain.entities.UserEntity
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    /**
     * Get the user by the given id
     * @param userId The wanted user id.
     *
     * @return A single [UserEntity] by the given id.
     */
    fun getUserById(userId: String): Flow<UserEntity?>

}
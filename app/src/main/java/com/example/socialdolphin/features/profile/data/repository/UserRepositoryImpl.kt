package com.example.socialdolphin.features.profile.data.repository

import com.example.socialdolphin.features.profile.data.datasource.local.IUserLocalDataSource
import com.example.socialdolphin.features.profile.domain.entities.UserEntity
import com.example.socialdolphin.features.profile.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: IUserLocalDataSource
) : IUserRepository {

    override fun getUserById(userId: String): Flow<UserEntity?> = flow {
        val realmPost = userLocalDataSource.getUserById(userId)
        emit(UserEntity.mapper(realmPost))
    }

}
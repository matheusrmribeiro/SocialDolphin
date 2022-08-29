package com.example.socialdolphin.features.profile.di

import com.example.socialdolphin.features.profile.data.datasource.local.IUserLocalDataSource
import com.example.socialdolphin.features.profile.data.datasource.local.UserLocalDataSourceImpl
import com.example.socialdolphin.features.profile.data.repository.UserRepositoryImpl
import com.example.socialdolphin.features.profile.domain.repository.IUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.RealmConfiguration

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    fun provideUserLocalDatasource(
        realmConfig: RealmConfiguration
    ): IUserLocalDataSource =
        UserLocalDataSourceImpl(
            realmConfig = realmConfig
        )

    @Provides
    fun provideUserRepository(
        userDataSource: IUserLocalDataSource,
    ): IUserRepository = UserRepositoryImpl(userLocalDataSource = userDataSource)

}
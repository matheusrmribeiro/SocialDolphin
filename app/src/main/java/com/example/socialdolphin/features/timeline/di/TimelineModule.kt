package com.example.socialdolphin.features.timeline.di

import com.example.socialdolphin.features.timeline.data.datasource.local.ITimelineLocalDataSource
import com.example.socialdolphin.features.timeline.data.datasource.local.TimelineLocalDataSourceImpl
import com.example.socialdolphin.features.timeline.data.repository.TimelineRepositoryImpl
import com.example.socialdolphin.features.timeline.domain.repository.ITimelineRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.RealmConfiguration

@Module
@InstallIn(SingletonComponent::class)
object TimelineModule {

    @Provides
    fun provideTimelineLocalDatasource(
        realmConfig: RealmConfiguration
    ): ITimelineLocalDataSource =
        TimelineLocalDataSourceImpl(
            realmConfig = realmConfig
        )

    @Provides
    fun provideTimelineRepository(
        timelineDataSource: ITimelineLocalDataSource
    ): ITimelineRepository = TimelineRepositoryImpl(timelineLocalDataSource = timelineDataSource)

}
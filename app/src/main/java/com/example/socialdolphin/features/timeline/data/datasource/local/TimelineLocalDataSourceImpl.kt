package com.example.socialdolphin.features.timeline.data.datasource.local

import com.example.socialdolphin.features.timeline.data.models.PostRealmObject
import com.example.socialdolphin.features.timeline.data.models.PostsTypeCountModel
import com.example.socialdolphin.features.timeline.domain.entities.PostTypeEnum
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class TimelineLocalDataSourceImpl @Inject constructor(
    private val realmConfig: RealmConfiguration
) : ITimelineLocalDataSource, CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.IO

    override fun createPost(model: PostRealmObject) {
        launch {
            val realm = Realm.getInstance(realmConfig)
            try {
                realm.executeTransactionAwait(Dispatchers.IO) {
                    realm.insertOrUpdate(model)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                realm.close()
            }
        }
    }

    override fun getTimeline(): List<PostRealmObject> {
        val realm = Realm.getInstance(realmConfig)
        try {
            val query = realm.where(PostRealmObject::class.java)
                .findAll()
                .sort("id", Sort.DESCENDING)
            return realm.copyFromRealm(query)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            realm.close()
        }
        return listOf()
    }

    override fun getPostsByUser(userId: String): List<PostRealmObject> {
        val realm = Realm.getInstance(realmConfig)
        try {
            val query = realm.where(PostRealmObject::class.java)
                .equalTo("creator.id", userId)
                .findAll()
                .sort("id", Sort.DESCENDING)
            return realm.copyFromRealm(query)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            realm.close()
        }
        return listOf()
    }

    override fun countPostsTypeByUser(userId: String): PostsTypeCountModel? {
        val realm = Realm.getInstance(realmConfig)
        try {
            val postsCount = realm.where(PostRealmObject::class.java)
                .equalTo("creator.id", userId)
                .equalTo("type", PostTypeEnum.POST.name.lowercase())
                .count()
            val repostsCount = realm.where(PostRealmObject::class.java)
                .equalTo("creator.id", userId)
                .equalTo("type", PostTypeEnum.REPOST.name.lowercase())
                .count()
            val quotesCount = realm.where(PostRealmObject::class.java)
                .equalTo("creator.id", userId)
                .equalTo("type", PostTypeEnum.QUOTE.name.lowercase())
                .count()
            return PostsTypeCountModel(
                posts = postsCount.toInt(),
                reposts = repostsCount.toInt(),
                quotes = quotesCount.toInt(),
            )
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            realm.close()
        }
        return null
    }

    override fun countPostsADayByUser(userId: String, date: String): Int? {
        val realm = Realm.getInstance(realmConfig)
        try {
            val postsCount = realm.where(PostRealmObject::class.java)
                .equalTo("creator.id", userId)
                .equalTo("createdAt", date)
                .count()
            return postsCount.toInt()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            realm.close()
        }
        return null
    }

}
package com.example.socialdolphin.features.profile.data.datasource.local

import com.example.socialdolphin.features.profile.data.models.UserRealmObject
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UserLocalDataSourceImpl @Inject constructor(
    private val realmConfig: RealmConfiguration
) : IUserLocalDataSource, CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.IO

    override fun getUserById(userId: String): UserRealmObject? {
        val realm = Realm.getInstance(realmConfig)
        try {
            val query = realm.where(UserRealmObject::class.java)
                .equalTo("id", userId)
                .findFirst()
            return query?.let { realm.copyFromRealm(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            realm.close()
        }
        return null
    }

}
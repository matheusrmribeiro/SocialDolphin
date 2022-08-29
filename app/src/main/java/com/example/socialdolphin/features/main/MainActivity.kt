package com.example.socialdolphin.features.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.example.socialdolphin.core.initialData.RealmInitialData
import com.example.socialdolphin.databinding.ActivityMainBinding
import com.example.socialdolphin.features.profile.data.models.UserRealmObject
import com.example.socialdolphin.features.profile.domain.entities.UserEntity
import com.example.socialdolphin.features.timeline.data.models.PostRealmObject
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var realmConfig: RealmConfiguration

    private var binding: ActivityMainBinding? = null
    private val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    companion object {
        /**
         * Only for the mock purpose
         */
        val currentUser: UserEntity
            get() = UserEntity.mapper(RealmInitialData.userData[0])!!
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingInflater(layoutInflater)
        binding?.let {
            setContentView(it.root)
        }
        setupActivity()
    }

    private fun setupActivity() {
        mockData()
    }

    /**
     * Only for the mock purpose
     */
    private fun mockData() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val realm = Realm.getInstance(realmConfig)
                try {

                    /* Insert user data */
                    val objUser = realm.where(UserRealmObject::class.java).findFirst()
                    if (objUser == null) {
                        realm.executeTransactionAwait(Dispatchers.IO) {
                            realm.insertOrUpdate(RealmInitialData.userData)
                        }
                    }

                    /* Insert Post data */
                    val objPost = realm.where(PostRealmObject::class.java).findFirst()
                    if (objPost == null) {
                        realm.executeTransactionAwait(Dispatchers.IO) {
                            realm.insertOrUpdate(RealmInitialData.timelineData)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    realm.close()
                }
            }
        }
    }
}
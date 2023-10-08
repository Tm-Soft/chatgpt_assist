package live.lafi.chatgpt_assist.base

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import live.lafi.chatgpt_assist.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class BaseApplication : Application() {
    init {
        instance = this
    }
    companion object {
        lateinit var instance: BaseApplication
        fun applicationContext() = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()

        // Timber 최초 초기화.
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }
}
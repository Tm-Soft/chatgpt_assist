package live.lafi.chatgpt_assist.base

import android.app.Application
import android.util.Log
import live.lafi.chatgpt_assist.BuildConfig
import timber.log.Timber

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
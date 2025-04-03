package xyz.rfsfernandes.tmdbdemo

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import xyz.rfsfernandes.tmdbdemo.data.network.NetworkManager
import xyz.rfsfernandes.tmdbdemo.di.DI

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin()
        NetworkManager(this)
    }

    private fun initializeKoin() {
        startKoin {
            androidContext(this@MyApplication)
            modules(DI.modules)
        }
    }
}

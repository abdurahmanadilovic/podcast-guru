package ba.codingstoic

import android.app.Application
import ba.codingstoic.di.koinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PodcastGuruApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PodcastGuruApplication)
            modules(listOf(koinModule))
        }
    }
}
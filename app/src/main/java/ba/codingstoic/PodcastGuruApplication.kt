package ba.codingstoic

import android.app.Application
import ba.codingstoic.di.dataModule
import ba.codingstoic.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PodcastGuruApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PodcastGuruApplication)
            modules(listOf(dataModule, presentationModule))
        }
    }
}
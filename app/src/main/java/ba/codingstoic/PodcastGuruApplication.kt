package ba.codingstoic

import android.app.Application
import ba.codingstoic.di.dataModule
import ba.codingstoic.di.presentationModule
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PodcastGuruApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        startKoin {
            androidContext(this@PodcastGuruApplication)
            modules(listOf(dataModule, presentationModule))
        }
    }
}
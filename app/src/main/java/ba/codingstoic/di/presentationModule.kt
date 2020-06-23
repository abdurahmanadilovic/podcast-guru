package ba.codingstoic.di

import android.content.ComponentName
import ba.codingstoic.player.MediaSessionConnection
import ba.codingstoic.player.PlayerService
import ba.codingstoic.player.PlayerViewModel
import ba.codingstoic.podcast.PodcastDetailsViewModel
import ba.codingstoic.podcast.PodcastListViewModel
import ba.codingstoic.user.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

/**
 * Created by Abdurahman Adilovic on 3/28/20.
 */

val presentationModule = module {
    factory<CoroutineContext> {
        SupervisorJob()
    }
    factory {
        Dispatchers.IO
    }
    viewModel {
        PlayerViewModel(get(), get(), get())
    }

    viewModel {
        PodcastListViewModel(get(), get(), get())
    }

    viewModel {
        PodcastDetailsViewModel(get(), get(), get())
    }

    viewModel {
        LoginViewModel(get())
    }
}
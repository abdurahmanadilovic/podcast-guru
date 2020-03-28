package ba.codingstoic.di

import ba.codingstoic.player.PlayerViewModel
import ba.codingstoic.podcast.PodcastDetailsViewModel
import ba.codingstoic.podcast.PodcastListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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
        PlayerViewModel(get(), get())
    }

    viewModel {
        PodcastListViewModel(get(), get(), get())
    }

    viewModel {
        PodcastDetailsViewModel(get(), get(), get())
    }
}
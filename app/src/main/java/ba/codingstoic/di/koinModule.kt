package ba.codingstoic.di

import ba.codingstoic.data.GPodderPodcastSource
import ba.codingstoic.player.PlayerViewModel
import ba.codingstoic.podcast.PodcastRepository
import ba.codingstoic.podcast.PodcastsViewModel
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val koinModule = module {
    single<ExoPlayer> {
        ExoPlayerFactory.newSimpleInstance(androidContext()).apply {
            setAudioAttributes(
                AudioAttributes.Builder().setContentType(C.CONTENT_TYPE_MUSIC)
                    .setUsage(C.USAGE_MEDIA).build(), true
            )
        }
    }

    single(named("userAgent")) {
        val userAgent = Util.getUserAgent(androidContext(), "Podcast guru android app")
        userAgent
    }

    single<DataSource.Factory> {
        DefaultHttpDataSourceFactory(
            get<String>(named("userAgent")),
            DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
            DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
            true
        )
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("http://gpodder.net/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    single<GPodderPodcastSource> {
        val retrofit: Retrofit = get()
        retrofit.create(GPodderPodcastSource::class.java)
    }

    single {
        PodcastRepository(get())
    }

    viewModel {
        PlayerViewModel(get(), get())
    }

    viewModel {
        PodcastsViewModel(get())
    }
}
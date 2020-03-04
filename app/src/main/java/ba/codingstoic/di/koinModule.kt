package ba.codingstoic.di

import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.audio.AudioAttributes
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val koinModule = module {
    single<ExoPlayer> {
        ExoPlayerFactory.newSimpleInstance(androidContext()).apply {
            setAudioAttributes(
                AudioAttributes.Builder().setContentType(C.CONTENT_TYPE_MUSIC)
                    .setUsage(C.USAGE_MEDIA).build(), true
            )
        }
    }
}
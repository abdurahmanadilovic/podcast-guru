package ba.codingstoic.player

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource

class PlayerViewModel(
    private val exoPlayer: ExoPlayer,
    private val dataSourceFactory: DataSource.Factory
) : ViewModel() {
    fun play(episodes: List<Episode>) {
        val mediaSources = episodes.map {
            ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(it.url))
        }.toTypedArray()
        exoPlayer.prepare(ConcatenatingMediaSource(*mediaSources))
        exoPlayer.playWhenReady = true
        // add listener
    }
}
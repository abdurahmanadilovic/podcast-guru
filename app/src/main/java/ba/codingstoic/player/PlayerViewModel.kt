package ba.codingstoic.player

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource

class PlayerViewModel(
    private val exoPlayer: ExoPlayer,
    private val dataSourceFactory: DataSource.Factory
) : ViewModel() {
    private val _currentlyPlaying = MutableLiveData<List<Episode>>()
    val currentlyPlaying: LiveData<List<Episode>> = _currentlyPlaying
    fun play(episodes: List<Episode>) {
        val mediaSources = episodes.map {
            ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(it.mp3Url))
        }.toTypedArray()
        exoPlayer.prepare(ConcatenatingMediaSource(*mediaSources))
        exoPlayer.playWhenReady = true
        _currentlyPlaying.value = episodes
    }
}
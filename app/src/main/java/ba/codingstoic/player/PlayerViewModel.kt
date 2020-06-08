package ba.codingstoic.player

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource

class PlayerViewModel(
    private val exoPlayer: ExoPlayer,
    private val dataSourceFactory: DataSource.Factory
) : ViewModel() {
    private val _currentlyPlaying = MutableLiveData<Episode>()
    val playlist = mutableListOf<Episode>()
    val currentlyPlaying: LiveData<Episode> = _currentlyPlaying
    private var currentIndex = 0
    private val listener = object : Player.EventListener {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            if (playbackState == Player.STATE_ENDED) {
                currentIndex += 1
                _currentlyPlaying.value = playlist[currentIndex]
            }
        }
    }

    init {
        exoPlayer.addListener(listener)
    }

    override fun onCleared() {
        exoPlayer.removeListener(listener)
    }

    fun play(currentEpisode: Episode, episodes: List<Episode>) {
        playlist.clear()
        playlist.add(currentEpisode)
        playlist.addAll(episodes.filter { it.mp3Url != currentEpisode.mp3Url })

        val mediaSources = playlist.map {
            ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(it.mp3Url))
        }.toTypedArray()
        exoPlayer.prepare(ConcatenatingMediaSource(*mediaSources))
        exoPlayer.playWhenReady = true

        _currentlyPlaying.value = episodes[0]
    }
}
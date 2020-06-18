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
    private val dataSourceFactory: DataSource.Factory,
    private val mediaSessionConnection: MediaSessionConnection
) : ViewModel() {
    private val _currentlyPlaying = MutableLiveData<Episode>()
    private val _playlist = mutableListOf<Episode>()
    val playlist
        get() = _playlist.subList(1, _playlist.size)
    val currentlyPlaying: LiveData<Episode> = _currentlyPlaying
    private var currentIndex = 0
    private val listener = object : Player.EventListener {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            if (playbackState == Player.STATE_ENDED) {
                currentIndex += 1
                _currentlyPlaying.value = _playlist[currentIndex]
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
        _playlist.clear()
        _playlist.add(currentEpisode)
        _playlist.addAll(episodes.filter { it.mp3Url != currentEpisode.mp3Url })

        val mediaSources = (listOf(currentEpisode) + _playlist).map {
            ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(it.mp3Url))
        }.toTypedArray()
        exoPlayer.prepare(ConcatenatingMediaSource(*mediaSources))
        exoPlayer.playWhenReady = true
        currentIndex = 0
        _currentlyPlaying.value = currentEpisode
    }

    fun playFromPlaylist(next: Episode) {
        val current = _playlist[currentIndex]
        val newPlaylist =
            _playlist.filter { it.mp3Url != next.mp3Url && it.mp3Url != current.mp3Url }

        _playlist.clear()
        _playlist.add(next)
        _playlist.addAll(newPlaylist)

        val mediaSources = (listOf(next) + _playlist).map {
            ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(it.mp3Url))
        }.toTypedArray()
        exoPlayer.prepare(ConcatenatingMediaSource(*mediaSources))
        exoPlayer.playWhenReady = true
        currentIndex = 0
        _currentlyPlaying.value = next
    }
}
package ba.codingstoic.player

import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource

class PlayerViewModel(val exoPlayer: ExoPlayer) : ViewModel() {
    fun play(episodes: List<Episode>) {
        exoPlayer.prepare(ConcatenatingMediaSource())
        exoPlayer.playWhenReady = true
        // add listener
    }
}
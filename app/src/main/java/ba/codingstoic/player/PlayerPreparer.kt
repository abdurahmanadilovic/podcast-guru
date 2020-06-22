package ba.codingstoic.player

import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.session.PlaybackStateCompat
import com.google.android.exoplayer2.ControlDispatcher
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.source.MediaSource

class PlaybackPreparer(val exoPlayer: ExoPlayer) : MediaSessionConnector.PlaybackPreparer {
    var mediaSource: MediaSource? = null


    override fun onPrepareFromSearch(query: String, playWhenReady: Boolean, extras: Bundle) {
    }

    override fun onCommand(
        player: Player,
        controlDispatcher: ControlDispatcher,
        command: String,
        extras: Bundle?,
        cb: ResultReceiver?
    ): Boolean {
        return false
    }

    override fun getSupportedPrepareActions(): Long {
        return PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID or PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID
    }

    override fun onPrepareFromMediaId(mediaId: String, playWhenReady: Boolean, extras: Bundle) {
        // set exoplayer to play
        mediaSource?.let {
            exoPlayer.prepare(it)
            exoPlayer.playWhenReady = true
        }
    }

    override fun onPrepareFromUri(uri: Uri, playWhenReady: Boolean, extras: Bundle) {
    }

    override fun onPrepare(playWhenReady: Boolean) {
    }

}


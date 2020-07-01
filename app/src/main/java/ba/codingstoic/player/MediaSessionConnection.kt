package ba.codingstoic.player

import android.content.ComponentName
import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat

class MediaSessionConnection(context: Context, serviceComponent: ComponentName) {
    private val mediaBrowser =
        MediaBrowserCompat(
            context,
            serviceComponent,
            MediaControllerCallback(context),
            null
        ).apply {
            connect()
        }

    private lateinit var mediaController: MediaControllerCompat
    val transportControls get() = mediaController.transportControls

    inner class MediaControllerCallback(val context: Context) :
        MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            super.onConnected()
            mediaController = MediaControllerCompat(context, mediaBrowser.sessionToken)
        }
    }
}
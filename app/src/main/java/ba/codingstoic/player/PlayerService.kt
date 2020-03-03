package ba.codingstoic.player

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.media.MediaBrowserServiceCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import org.koin.android.ext.android.inject

class PlayerService : MediaBrowserServiceCompat() {
    private lateinit var notificationManager: NotificationManagerCompat
    private val exoPlayer: ExoPlayer by inject()
    private val nowPlayingChannelId: String = "ba.codingstoic.NOW_PLAYING"
    private val nowPlayingNotificationId: Int = 1
    private lateinit var mediaSession: MediaSessionCompat

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        super.onCreate()

        val sessionActivityPendingIntent =
            packageManager?.getLaunchIntentForPackage(packageName)?.let { sessionIntent ->
                PendingIntent.getActivity(this, 1, sessionIntent, 0)
            }

        mediaSession = MediaSessionCompat(this, "Player Service")
            .apply {
                setSessionActivity(sessionActivityPendingIntent)
                isActive = true
            }

        sessionToken = mediaSession.sessionToken

        notificationManager = NotificationManagerCompat.from(this)

        if (shouldCreateNowPlayingChannel(notificationManager)) {
            createNowPlayingChannel(notificationManager)
        }

        sessionToken?.let {
            val playerNotificationManager = PlayerNotificationManager(this, nowPlayingChannelId,
                nowPlayingNotificationId,
                object : PlayerNotificationManager.MediaDescriptionAdapter {
                    override fun getCurrentContentText(player: Player?): String? {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun getCurrentContentTitle(player: Player?): String {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun getCurrentLargeIcon(
                        player: Player?,
                        callback: PlayerNotificationManager.BitmapCallback?
                    ): Bitmap? {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun createCurrentContentIntent(player: Player?): PendingIntent? {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }, object : PlayerNotificationManager.NotificationListener {
                    override fun onNotificationPosted(
                        notificationId: Int,
                        notification: Notification?,
                        ongoing: Boolean
                    ) {
                    }
                })

            playerNotificationManager.setPlayer(exoPlayer)
            playerNotificationManager.setMediaSessionToken(it)
        }
    }

    private fun shouldCreateNowPlayingChannel(notificationManager: NotificationManagerCompat) =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !nowPlayingChannelExists(
            notificationManager
        )

    @RequiresApi(Build.VERSION_CODES.O)
    private fun nowPlayingChannelExists(notificationManager: NotificationManagerCompat) =
        notificationManager.getNotificationChannel(nowPlayingChannelId) != null

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNowPlayingChannel(notificationManager: NotificationManagerCompat) {
        val notificationChannel = NotificationChannel(
            nowPlayingChannelId,
            "Now playing channel",
            NotificationManager.IMPORTANCE_LOW
        )
            .apply {
                description = "Now playing channel"
            }

        notificationManager.createNotificationChannel(notificationChannel)
    }
}

private class BecomingNoisyReceiver(
    private val context: Context,
    sessionToken: MediaSessionCompat.Token
) : BroadcastReceiver() {

    private val noisyIntentFilter = IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY)
    private val controller = MediaControllerCompat(context, sessionToken)

    private var registered = false

    fun register() {
        if (!registered) {
            context.registerReceiver(this, noisyIntentFilter)
            registered = true
        }
    }

    fun unregister() {
        if (registered) {
            context.unregisterReceiver(this)
            registered = false
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == AudioManager.ACTION_AUDIO_BECOMING_NOISY) {
            controller.transportControls.pause()
        }
    }
}
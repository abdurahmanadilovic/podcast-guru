package ba.codingstoic

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import ba.codingstoic.player.PlayerViewModel
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val playerViewModel: PlayerViewModel by viewModel()
    private val exoPlayer: ExoPlayer by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        player_view.player = exoPlayer

        playerViewModel.currentlyPlaying.observe(this, Observer {
            main_activity_bottom_sheet.visibility = View.VISIBLE
        })
    }
}

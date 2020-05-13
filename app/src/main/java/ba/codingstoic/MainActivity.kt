package ba.codingstoic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import ba.codingstoic.player.PlayerViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val playerViewModel: PlayerViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerViewModel.currentlyPlaying.observe(this, Observer {
            BottomSheetBehavior.from(main_activity_bottom_sheet).state =
                BottomSheetBehavior.STATE_EXPANDED
        })
    }
}

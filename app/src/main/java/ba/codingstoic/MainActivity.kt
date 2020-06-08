package ba.codingstoic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ba.codingstoic.databinding.ActivityMainBinding
import ba.codingstoic.player.PlayerViewModel
import ba.codingstoic.podcast.EpisodeRow
import ba.codingstoic.podcast.SectionHeader
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val playerViewModel: PlayerViewModel by viewModel()
    private val exoPlayer: ExoPlayer by inject()
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.playerView.player = exoPlayer

        val collapsedBehaviour = BottomSheetBehavior.from(binding.mainActivityCollapsedPlayer)
        collapsedBehaviour.state = BottomSheetBehavior.STATE_HIDDEN

        binding.mainActivityCollapsedPlayer.setOnClickListener {
            collapsedBehaviour.state = BottomSheetBehavior.STATE_EXPANDED
        }

        val playlistAdapter = GroupAdapter<GroupieViewHolder>()
        val playlistSection = Section()
        playlistSection.setHeader(SectionHeader("Next"))

        playerViewModel.currentlyPlaying.observe(this, Observer { episode ->
            binding.currentEpisodeTitle.text = episode.title
            playlistSection.clear()
            playlistSection.addAll(playerViewModel.playlist.map { EpisodeRow(it) })
        })

        playlistAdapter.add(playlistSection)

        binding.playlistRv.apply {
            adapter = playlistAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}

package ba.codingstoic


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import ba.codingstoic.player.Episode
import ba.codingstoic.player.PlayerViewModel
import ba.codingstoic.podcast.PodcastsViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {
    private val podcastsViewModel: PodcastsViewModel by viewModel()
    private val playerViewModel: PlayerViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        podcastsViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            loading_indicator.visibility = if (it) View.VISIBLE else View.GONE
            content_group.visibility = if (it) View.GONE else View.VISIBLE
        })
        podcastsViewModel.podcasts.observe(viewLifecycleOwner, Observer {
            podcast_title.text = it.first().name
        })

        podcastsViewModel.getPodcasts()

        playerViewModel.play(
            listOf(
                Episode(
                    1,
                    "https://traffic.libsyn.com/joeroganexp/p1438a.mp3"
                )
            )
        )
    }
}

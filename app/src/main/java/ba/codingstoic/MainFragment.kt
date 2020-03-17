package ba.codingstoic


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ba.codingstoic.player.PlayerViewModel
import ba.codingstoic.podcast.Podcast
import ba.codingstoic.podcast.PodcastsViewModel
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
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

        val adapter = RendererRecyclerViewAdapter().apply {
            registerRenderer(PodcastRowBinder(object : PodcastRowClickListener {
                override fun podcastClicked(podcast: Podcast) {
                    playerViewModel.play(podcast.episodes)
                }
            }))
        }

        podcastsViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            loading_indicator.visibility = if (it) View.VISIBLE else View.GONE
        })

        podcastsViewModel.podcasts.observe(viewLifecycleOwner, Observer {
            adapter.setItems(it)
            adapter.notifyDataSetChanged()
        })

        podcast_rv.layoutManager = LinearLayoutManager(context)
        podcast_rv.adapter = adapter

        podcastsViewModel.getPodcasts()
    }

    interface PodcastRowClickListener {
        fun podcastClicked(podcast: Podcast)
    }

    class PodcastRowBinder(podcastRowClickListener : PodcastRowClickListener):
        ViewBinder<Podcast>(R.layout.podcast_row, Podcast::class.java,
        { model, finder, payloads ->
            finder.setText(R.id.podcast_title, model.name)
            finder.setOnClickListener(R.id.podcast_root) {
                podcastRowClickListener.podcastClicked(model)
            }
        })
}

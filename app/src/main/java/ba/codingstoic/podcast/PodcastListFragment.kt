package ba.codingstoic.podcast


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ba.codingstoic.R
import ba.codingstoic.player.PlayerViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.podcast_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 */
class PodcastListFragment : Fragment() {
    private val podcastListViewModel: PodcastListViewModel by viewModel()
    private val playerViewModel: PlayerViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.podcast_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newAdapter = GroupAdapter<GroupieViewHolder>()
        val topSection = Section()
        topSection.setHeader(PodcastSectionHeader("Top"))

        newAdapter.add(topSection)

        newAdapter.setOnItemClickListener { item, _ ->
            if (item is PodcastItem) {
                findNavController().navigate(
                    R.id.action_mainFragment_to_podcastDetailsFragment,
                    bundleOf(PodcastDetailsFragment.podcastIdArgument to item.podcast.id)
                )
            }
        }

        podcast_list_swipe_refresh.setOnRefreshListener {
            podcastListViewModel.getPodcasts()
        }

        podcastListViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            podcast_list_swipe_refresh.isRefreshing = it
        })

        podcastListViewModel.podcasts.observe(viewLifecycleOwner, Observer { list ->
            topSection.clear()
            topSection.addAll(list.map { PodcastItem(it) })
        })

        podcast_list_rv.layoutManager = LinearLayoutManager(context)
        podcast_list_rv.adapter = newAdapter

        podcastListViewModel.getPodcasts()
    }
}

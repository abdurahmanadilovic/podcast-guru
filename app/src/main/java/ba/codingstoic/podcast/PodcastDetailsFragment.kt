package ba.codingstoic.podcast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ba.codingstoic.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.podcast_details_view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Abdurahman Adilovic on 3/22/20.
 */

class PodcastDetailsFragment : Fragment() {
    private val podcastDetailsViewModel by viewModel<PodcastDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.podcast_details_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GroupAdapter<GroupieViewHolder>()

        podcastDetailsViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            podcast_details_swipe_refresh.isRefreshing = it
        })

        podcastDetailsViewModel.podcast.observe(viewLifecycleOwner, Observer {
        })

        podcast_details_swipe_refresh.setOnRefreshListener {
            podcastDetailsViewModel.getPodcastDetails()
        }

        podcast_details_rv.adapter = adapter
        podcast_details_rv.layoutManager = LinearLayoutManager(context)

        podcastDetailsViewModel.getPodcastDetails()
    }

}
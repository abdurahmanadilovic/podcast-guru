package ba.codingstoic.podcast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ba.codingstoic.player.Episode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Abdurahman Adilovic on 3/22/20.
 */

class PodcastDetailsViewModel(private val podcastRepository: PodcastRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _podcast = MutableLiveData<Podcast>()
    val podcast: LiveData<Podcast> = _podcast

    fun getPodcastDetails(urlId: String) {
        viewModelScope.launch {
            val podcastDetails = withContext(Dispatchers.IO) {
                podcastRepository.getPodcast(urlId)
            }
            val episodes = withContext(Dispatchers.IO) {
                podcastRepository.getEpisodes(urlId).map {
                    Episode(it.title, it.url)
                }
            }

            Podcast(podcastDetails.url, podcastDetails.title, podcastDetails.logo_url, episodes)
        }
    }
}
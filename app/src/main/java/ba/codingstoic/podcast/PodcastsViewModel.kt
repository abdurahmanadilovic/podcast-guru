package ba.codingstoic.podcast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class PodcastsViewModel : androidx.lifecycle.ViewModel() {
    private val _podcasts = MutableLiveData<PodcastCategories>()
    val podcasts: LiveData<PodcastCategories> = _podcasts
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val podcastRepository = PodcastRepository()

    fun getPodcasts() {
        _isLoading.value = true
        viewModelScope.launch {
            delay(TimeUnit.SECONDS.toMillis(2))
            val hotPodcasts = podcastRepository.getHotPodcasts()
            val newestPodcasts = podcastRepository.getNewestPodcasts()
            _podcasts.value = PodcastCategories(hotPodcasts, newestPodcasts)
            _isLoading.value = false
        }
    }
}

data class PodcastCategories(val hot: List<Podcast>, val newest: List<Podcast>)
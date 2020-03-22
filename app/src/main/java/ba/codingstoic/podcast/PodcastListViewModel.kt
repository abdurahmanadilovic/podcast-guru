package ba.codingstoic.podcast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PodcastListViewModel(private val podcastRepository: PodcastRepository) : ViewModel() {
    private val _podcasts = MutableLiveData<List<Podcast>>()
    val podcasts: LiveData<List<Podcast>> = _podcasts
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getPodcasts() {
        _isLoading.value = true
        viewModelScope.launch {
            val podcasts = withContext(Dispatchers.IO) {
                podcastRepository.getTopPodcasts()
            }
            _podcasts.value = podcasts
            _isLoading.value = false
        }
    }
}


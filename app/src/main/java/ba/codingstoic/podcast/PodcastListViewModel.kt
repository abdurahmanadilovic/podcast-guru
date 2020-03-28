package ba.codingstoic.podcast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class PodcastListViewModel(
    private val podcastRepository: PodcastRepository,
    private val coroutineContext: CoroutineContext,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _podcasts = MutableLiveData<List<Podcast>>()
    val podcasts: LiveData<List<Podcast>> = _podcasts
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _errors = MutableLiveData<String>()
    val errors: LiveData<String> = _errors

    fun getPodcasts() {
        viewModelScope.launch(coroutineContext) {
            _isLoading.value = true
            try {
                val podcasts = withContext(coroutineDispatcher) {
                    podcastRepository.getTopPodcasts()
                }
                _podcasts.value = podcasts
            } catch (ex: Exception) {
                _errors.value = ex.localizedMessage
            }

            _isLoading.value = false
        }
    }
}


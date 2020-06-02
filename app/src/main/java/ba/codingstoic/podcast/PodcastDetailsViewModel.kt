package ba.codingstoic.podcast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * Created by Abdurahman Adilovic on 3/22/20.
 */

class PodcastDetailsViewModel(
    private val podcastRepository: PodcastRepository,
    private val coroutineContext: CoroutineContext,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _errors = MutableLiveData<String>()
    val errors: LiveData<String> = _errors
    private val _podcast = MutableLiveData<Podcast>()
    val podcastAndEpisodes: LiveData<Podcast> = _podcast

    fun getPodcastDetails(id: String) {
        viewModelScope.launch(coroutineContext) {
            _isLoading.value = true

            try {
                val podcastDetails = withContext(ioDispatcher) {
                    podcastRepository.getPodcast(id)
                }
                _podcast.value = podcastDetails
            } catch (ex: Exception) {
                _errors.value = ex.localizedMessage
            }

            _isLoading.value = false
        }
    }
}
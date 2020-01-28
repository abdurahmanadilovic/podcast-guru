package ba.codingstoic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

data class Podcast(val name: String)

class PodcastsViewModel : ViewModel() {
    private val _podcasts = MutableLiveData<List<Podcast>>()
    val podcasts: LiveData<List<Podcast>> = _podcasts
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getPodcasts() {
        _isLoading.value = true
        viewModelScope.launch {
            delay(TimeUnit.SECONDS.toMillis(2))
            _podcasts.value = listOf(Podcast("The Joe Rogan experience"))
            _isLoading.value = false
        }
    }
}
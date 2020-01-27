package ba.codingstoic

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

data class Podcast(val name: String)

class PodcastsViewModel: ViewModel() {
    val _podcasts = MutableLiveData<List<Podcast>>()
    val podcasts: LiveData<List<Podcast>> = _podcasts

    fun getPodcasts(){
        viewModelScope.launch {
            delay(TimeUnit.SECONDS.toMillis(2))
            _podcasts.value = listOf(Podcast("The Joe Rogan experience"))
        }
    }
}
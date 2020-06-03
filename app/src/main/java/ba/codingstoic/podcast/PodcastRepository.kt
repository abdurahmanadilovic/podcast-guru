package ba.codingstoic.podcast

import android.content.Context
import ba.codingstoic.R
import ba.codingstoic.player.Episode
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.util.*


data class Podcast(
    val id: String,
    val name: String,
    val imageUrl: String,
    val feedUrl: String,
    val episodes: List<Episode>
)

class PodcastRepository(context: Context) {
    var podcasts: List<Podcast>

    init {
        val inputStream = context.resources.openRawResource(R.raw.data)
        val jsonString: String = Scanner(inputStream).useDelimiter("\\A").next()
        val moshi = Moshi.Builder().build()
        val podcastType = Types.newParameterizedType(List::class.java, Podcast::class.java)
        val adapter = moshi.adapter<List<Podcast>>(podcastType)
        podcasts = adapter.fromJson(jsonString) ?: listOf()
    }

    fun getTopPodcasts(): List<Podcast>? {
        return podcasts
    }

    fun getPodcast(id: String): Podcast {
        return podcasts.get(podcasts.indexOfFirst { it.id == id })
    }
}
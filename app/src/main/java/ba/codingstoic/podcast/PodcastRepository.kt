package ba.codingstoic.podcast

import ba.codingstoic.player.Episode
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel

data class Podcast(val name: String, val episodes: List<Episode>): ViewModel

class PodcastRepository {
    fun getPodcasts(): List<Podcast> =
        listOf(
            Podcast("The Joe Rogan experience", listOf(Episode(1, "")))
        )
}
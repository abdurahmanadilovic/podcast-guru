package ba.codingstoic.podcast

import ba.codingstoic.data.GPodderPodcastSource
import ba.codingstoic.player.Episode

data class Podcast(val name: String, val episodes: List<Episode>)

class PodcastRepository(private val podcastSource: GPodderPodcastSource) {
    suspend fun getTopPodcasts(count: Int = 10): List<Podcast> {
        return podcastSource.getTopPodcasts(count).map {
            Podcast(it.title, listOf())
        }
    }

    fun getHotPodcasts(): List<Podcast> =
        listOf(
            Podcast("The Joe Rogan experience", listOf(Episode(1, ""))),
            Podcast("What will you learn", listOf(Episode(2, "")))
        )

    fun getNewestPodcasts(): List<Podcast> =
        listOf(
            Podcast("Corona podcast", listOf(Episode(3, ""))),
            Podcast("Year 2020 podcast", listOf(Episode(4, "")))
        )

}
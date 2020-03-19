package ba.codingstoic.podcast

import ba.codingstoic.player.Episode

data class Podcast(val name: String, val episodes: List<Episode>)

class PodcastRepository {
    fun getPodcasts(): List<Podcast> =
        listOf(
            Podcast("The Joe Rogan experience", listOf(Episode(1, ""))),
            Podcast("What will you learn", listOf(Episode(2, "")))
        )
}
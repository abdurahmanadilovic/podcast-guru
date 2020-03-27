package ba.codingstoic.podcast

import ba.codingstoic.data.GPodderEpisodeModel
import ba.codingstoic.data.GPodderPodcastModel
import ba.codingstoic.data.GPodderPodcastSource
import ba.codingstoic.player.Episode

data class Podcast(
    val urlId: String,
    val name: String,
    val imageUrl: String,
    val episodes: List<Episode>
)

class PodcastRepository(private val podcastSource: GPodderPodcastSource) {
    suspend fun getTopPodcasts(count: Int = 10): List<Podcast> {
        return podcastSource.getTopPodcasts(count).map {
            Podcast(it.url, it.title, it.logo_url, listOf())
        }
    }

    suspend fun getPodcast(id: String): GPodderPodcastModel {
        return podcastSource.getPodcast(id)
    }

    fun getEpisodes(podcastId: String): List<GPodderEpisodeModel> {
        return listOf()
    }
}
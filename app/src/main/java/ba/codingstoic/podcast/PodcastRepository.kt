package ba.codingstoic.podcast

import ba.codingstoic.data.Entry
import ba.codingstoic.data.GPodderEpisodeModel
import ba.codingstoic.data.GPodderPodcastModel
import ba.codingstoic.data.GPodderPodcastSource

data class Podcast(
    val urlId: String,
    val name: String,
    val imageUrl: String?
) {
    companion object {
        fun fromItunesModel(entry: Entry): Podcast {
            val id = entry.id.attributes.id
            val name = entry.title.label
            val imageUrl = entry.images?.getOrNull(0)?.label
            return Podcast(id, name, imageUrl)
        }
    }
}

class PodcastRepository(private val podcastSource: GPodderPodcastSource) {
    suspend fun getTopPodcasts(count: Int = 10): List<Podcast>? {
        val url = "https://itunes.apple.com/us/rss/toppodcasts/limit=$count/explicit=true/json"
        val data = podcastSource.getTopPodcastsItunes(url)
        return data.feed.entry.map {
            Podcast.fromItunesModel(it)
        }
    }

    suspend fun getPodcast(id: String): GPodderPodcastModel {
        return podcastSource.getPodcast(id)
    }

    fun getEpisodes(podcastId: String): List<GPodderEpisodeModel> {
        val feedUrl = "https://itunes.apple.com/lookup?id=$podcastId"
        return listOf()
    }
}
package ba.codingstoic.podcast

import ba.codingstoic.data.Entry
import ba.codingstoic.data.FeedSource
import ba.codingstoic.data.ItunesSinglePodcast
import ba.codingstoic.data.NetworkSource
import ba.codingstoic.player.Episode

data class Podcast(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val feedUrl: String? = null
) {
    companion object {
        fun fromItunesModel(entry: Entry): Podcast {
            val id = if (entry.id.attributes.id.isNullOrEmpty()) entry.id.label.split("/").last()
                .replace("id", "").split("?").first() else entry.id.attributes.id
            val name = entry.title.label
            val imageUrl = entry.images?.getOrNull(0)?.label
            return Podcast(id = id, name = name, imageUrl = imageUrl)
        }

        fun fromSingleItunesModel(itunesModel: ItunesSinglePodcast): Podcast {
            return Podcast(
                name = itunesModel.collectionName,
                feedUrl = itunesModel.feedUrl,
                imageUrl = itunesModel.artworkUrl100,
                id = itunesModel.collectionId.toString()
            )
        }
    }
}

class PodcastRepository(
    private val podcastSource: NetworkSource,
    private val feedSource: FeedSource
) {
    suspend fun getTopPodcasts(count: Int = 10): List<Podcast>? {
        val url = "https://itunes.apple.com/us/rss/toppodcasts/limit=$count/explicit=true/json"
        val data = podcastSource.getTopPodcastsItunes(url)
        return data.feed.entry.map {
            Podcast.fromItunesModel(it)
        }
    }

    suspend fun getPodcast(id: String): Podcast {
        val url = "https://itunes.apple.com/lookup?id=$id"
        val itunesModel = podcastSource.getSinglePodcast(url).results.get(0)
        return Podcast.fromSingleItunesModel(itunesModel)
    }

    suspend fun getEpisodes(feedUrl: String): List<Episode> {
        val episodes = feedSource.getEpisodes(feedUrl).channel?.items?.map {
            Episode(title = it.title, mp3Url = it.enclosure.url, imageUrl = it.imageUrl?.url)
        }
        return episodes ?: listOf()
    }
}
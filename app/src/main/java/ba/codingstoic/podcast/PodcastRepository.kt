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
    val podcats = listOf(
        Pair(
            Podcast(
                id = "1",
                name = "Call Her Daddy",
                imageUrl = "https://chumley.barstoolsports.com/union/2020/05/25/3132d057.jpg",
                feedUrl = "https://mcsorleys.barstoolsports.com/feed/call-her-daddy"
            ),
            listOf(
                Episode(
                    title = "The Funeral",
                    date = "Wed, 27 May 2020 01:42:21 GMT",
                    duration = "01:00:11",
                    mp3Url = "https://dts.podtrac.com/redirect.mp3/landmark.barstoolsports.net/call-her-daddy/38065/funeralfinal.23f1dfbd.mp3"
                ),
                Episode(
                    title = "Daddy Speaks",
                    date = "Mon, 18 May 2020 00:19:14 GMT",
                    duration = "00:29:03",
                    mp3Url = "https://chumley.barstoolsports.com/union/2020/05/25/3132d057.jpg"
                ),
                Episode(
                    title = "How To Keep Them Interested During Quarantine",
                    date = "Wed, 08 Apr 2020 07:04:59 GMT",
                    duration = "00:59:04",
                    mp3Url = "https://chumley.barstoolsports.com/union/2020/05/25/3132d057.jpg"
                )
            )
        )
    )

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
            Episode(title = it.title ?: "", date = "", duration = "", mp3Url = it.enclosure.url)
        }
        return episodes ?: listOf()
    }
}
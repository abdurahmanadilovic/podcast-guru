package ba.codingstoic.data

import com.squareup.moshi.Json

/**
 * Created by Abdurahman Adilovic on 3/21/20.
 */

data class GPodderPodcastModel(
    val website: String,
    val description: String,
    val title: String,
    val author: String,
    val url: String,
    val subscribers: Int,
    val position_last_week: Int,
    val mygpo_link: String,
    val logo_url: String
)

data class GPodderEpisodeModel(
    val title: String,
    val url: String,
    val podcast_title: String,
    val podcast_url: String,
    val description: List<String>,
    val website: String,
    val released: String,
    val mygpo_link: String
)


// itunes top podcasts models

data class Attributes(@Json(name = "im:id") val id: String)
data class Id(val attributes: Attributes)
data class Image(val label: String)
data class LabelProperty(val label: String)
data class Entry(
    val id: Id,
    val title: LabelProperty,
    @Json(name = "im:image") val images: List<Image>?,
    val summary: LabelProperty
)

data class Feed(val entry: List<Entry>)
data class ItunesTopPodcastsModel(val feed: Feed)


// itunes single podcast models

data class ItunesSinglePodcast(
    val feedUrl: String
)

data class ItunesSinglePodcastWrapper(
    val results: List<ItunesSinglePodcast>
)


// rss models

data class Enclosure(val url: String)

data class RssItem(
    val url: String,
    @Json(name = "itunes:title")
    val title: String,
    @Json(name = "itunes:summary")
    val summary: String,
    @Json(name = "pubDate")
    val publishedDate: String,
    val enclosure: Enclosure
)

data class Channel(
    val item: List<RssItem>
)

data class Rss(
    val channel: Channel
)

data class RssWrapper(val rss: Rss)


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

data class Entries(val entry: List<Entry>)
data class Feed(val entry: List<Entry>)
data class ItunesModel(val feed: Feed)
data class ItunesEpisode(val url: String)


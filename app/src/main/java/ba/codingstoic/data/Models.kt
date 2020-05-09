package ba.codingstoic.data

import com.squareup.moshi.Json
import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

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

data class Attributes(@Json(name = "im:id") val id: String?)
data class Id(val attributes: Attributes, val label: String)
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
    val collectionId: Long,
    val collectionName: String,
    val artworkUrl100: String,
    val feedUrl: String
)

data class ItunesSinglePodcastWrapper(
    val results: List<ItunesSinglePodcast>
)


// rss models

@Xml
data class Enclosure(@Attribute var url: String)

@Xml
data class Item(
    @PropertyElement(name = "itunes:title")
    var title: String,
    @PropertyElement(name = "itunes:summary")
    var summary: String,
    @PropertyElement(name = "pubDate")
    var publishedDate: String,
    @Element
    var enclosure: Enclosure
)

@Xml
data class Channel(
    @Element
    var items: List<Item>
)

@Xml
data class Rss constructor(
    @Element var channel: Channel? = null
)

@Xml()
data class RssWrapper @JvmOverloads constructor(@field:Element var rss: Rss? = null)


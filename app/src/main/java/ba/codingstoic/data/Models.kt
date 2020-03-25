package ba.codingstoic.data

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
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
    val mygpo_link: String
)
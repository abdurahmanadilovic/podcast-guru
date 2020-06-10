package ba.codingstoic.podcast

import androidx.recyclerview.widget.ItemTouchHelper
import ba.codingstoic.R
import ba.codingstoic.player.Episode
import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.podcast_details_episode_row.*
import kotlinx.android.synthetic.main.podcast_details_podcast_row.*
import kotlinx.android.synthetic.main.podcast_row.*
import kotlinx.android.synthetic.main.podcast_section_header.*

/**
 * Created by Abdurahman Adilovic on 3/19/20.
 */

class PodcastItem(val podcast: Podcast) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.podcast_title.text = podcast.name
        viewHolder.podcast_image.transitionName = podcast.id
        Picasso.get().load(podcast.imageUrl).into(viewHolder.podcast_image)
    }

    override fun getLayout(): Int = R.layout.podcast_row

    override fun getDragDirs(): Int {
        return ItemTouchHelper.UP or ItemTouchHelper.DOWN
    }

    override fun getSwipeDirs(): Int {
        return ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    }
}

class SectionHeader(private val name: String) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.podcast_section_header.text = name
    }

    override fun getLayout(): Int = R.layout.podcast_section_header

}

class PodcastDetailsItem(val podcast: Podcast) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.podcast_details_podcast_row_image.transitionName = podcast.id
        Picasso.get().load(podcast.imageUrl).into(viewHolder.podcast_details_podcast_row_image)
    }

    override fun getLayout(): Int = R.layout.podcast_details_podcast_row

}

class EpisodeRow(val episode: Episode) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.episode_row_title.text = episode.title
    }

    override fun getLayout(): Int = R.layout.podcast_details_episode_row

}


class PlaylistRow(val episode: Episode) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.episode_row_title.text = episode.title
    }

    override fun getLayout(): Int {
        return R.layout.playlist_row
    }
}


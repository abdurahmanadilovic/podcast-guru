package ba.codingstoic.podcast

class PodcastRepository {
    fun getPodcasts(): List<Podcast> =
        listOf(Podcast("The Joe Rogan experience"))
}
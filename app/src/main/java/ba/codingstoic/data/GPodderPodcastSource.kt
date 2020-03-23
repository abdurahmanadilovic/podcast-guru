package ba.codingstoic.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Abdurahman Adilovic on 3/21/20.
 */

interface GPodderPodcastSource {
    @GET("toplist/{count}.json")
    suspend fun getTopPodcasts(@Path("count") count: Int): List<GPodderPodcastModel>

    @GET("api/2/data/podcast.json")
    suspend fun getPodcast(@Query("url") url: String): GPodderPodcastModel
}
package ba.codingstoic.data

import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Abdurahman Adilovic on 3/21/20.
 */

interface GPodderPodcastSource {
    @POST("api/2/auth/{username}/login.json")
    suspend fun login(
        @Header("Authorization") authHeader: String,
        @Path("username") username: String
    ): Response<Void>

    @GET("toplist/{count}.json")
    suspend fun getTopPodcasts(@Path("count") count: Int): List<GPodderPodcastModel>

    @GET("api/2/data/podcast.json")
    suspend fun getPodcast(@Query("url") url: String): GPodderPodcastModel

    @GET
    suspend fun getTopPodcastsItunes(@Url url: String): ItunesModel
}
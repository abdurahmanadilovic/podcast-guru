package ba.codingstoic.data

import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Abdurahman Adilovic on 3/21/20.
 */

interface NetworkSource {
    @POST("api/2/auth/{username}/login.json")
    suspend fun login(
        @Header("Authorization") authHeader: String,
        @Path("username") username: String
    ): Response<Void>

    @GET
    suspend fun getTopPodcastsItunes(@Url url: String): ItunesTopPodcastsModel

    @GET
    suspend fun getSinglePodcast(@Url url: String): ItunesSinglePodcastWrapper
}

interface FeedSource {
    @GET
    suspend fun getEpisodes(@Url url: String): Rss
}
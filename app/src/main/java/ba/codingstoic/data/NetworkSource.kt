package ba.codingstoic.data

import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by Abdurahman Adilovic on 3/21/20.
 */

interface NetworkSource {
    @POST("api/2/auth/{username}/login.json")
    suspend fun login(
        @Header("Authorization") authHeader: String,
        @Path("username") username: String
    ): Response<Void>
}


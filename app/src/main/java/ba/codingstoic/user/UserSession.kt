package ba.codingstoic.user

import android.util.Base64
import ba.codingstoic.data.GPodderPodcastSource


/**
 * Created by Abdurahman Adilovic on 3/28/20.
 */

class UserSession(
    private val gPodderPodcastSource: GPodderPodcastSource,
    private val cookieManager: CookieManager
) {

    suspend fun loginUser(username: String, password: String) {
        val credentials = "$username:$password"
        val basic = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
        val response = gPodderPodcastSource.login(authHeader = basic, username = username)
        val cookieValues = response.headers()["Set-Cookie"]?.split(";")
        cookieValues?.let {
            val sessionId = it.firstOrNull()
            val expires = it.getOrNull(1)
            sessionId?.let {
                cookieManager.storeCookie(it)
                return
            }
        }

        throw IllegalStateException("Could not read sessionId!")
    }

    fun isLoggedIn(): Boolean {
        return cookieManager.getCookie().isNotBlank()
    }
}
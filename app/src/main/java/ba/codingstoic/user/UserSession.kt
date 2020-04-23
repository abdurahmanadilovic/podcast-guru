package ba.codingstoic.user

import android.util.Base64
import ba.codingstoic.data.NetworkSource


/**
 * Created by Abdurahman Adilovic on 3/28/20.
 */

class UserSession(
    private val networkSource: NetworkSource,
    private val cookieManager: CookieManager
) {

    suspend fun loginUser(username: String, password: String) {
        val response = networkSource.login(
            authHeader = getBasicAuthHeader(username, password),
            username = username
        )
        val cookieValues = response.headers()["Set-Cookie"]?.split(";")
        cookieValues?.let {
            val sessionId = it.firstOrNull()
            val expires = it.getOrNull(1)

            sessionId?.let { sessionId ->
                expires?.let { expiresAt ->
                    cookieManager.storeExpiresAt(expiresAt.split("=")[1])
                    cookieManager.storeCookie(sessionId.split("=")[1])
                    cookieManager.storeUserNameAndPassword(Pair(username, password))
                    return
                }
            }
        }

        throw IllegalStateException("Could not read sessionId!")
    }

    private fun getBasicAuthHeader(username: String, password: String): String {
        val credentials = "$username:$password"
        return "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
    }

    fun isLoggedIn(): Boolean {
        return true
    }
}
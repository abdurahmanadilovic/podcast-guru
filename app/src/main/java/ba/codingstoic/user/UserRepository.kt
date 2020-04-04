package ba.codingstoic.user

import android.content.SharedPreferences
import android.util.Base64
import androidx.core.content.edit
import ba.codingstoic.data.GPodderPodcastSource


/**
 * Created by Abdurahman Adilovic on 3/28/20.
 */

class UserRepository(
    private val gPodderPodcastSource: GPodderPodcastSource,
    private val sharedPreferences: SharedPreferences
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
                sharedPreferences.edit {
                    putString("cookie", it)
                }
                return
            }
        }

        throw IllegalStateException("Could not read sessionId!")
    }

    fun getCookie(): String {
        return sharedPreferences.getString("cookie", "") ?: ""
    }
}
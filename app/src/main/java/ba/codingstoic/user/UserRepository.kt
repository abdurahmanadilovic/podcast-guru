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
        val cookie = response.headers()["sessionId"]
        cookie?.let {
            sharedPreferences.edit {
                putString("cookie", it)
            }
        }
    }

    fun getCookie(): String {
        return sharedPreferences.getString("cookie", "") ?: ""
    }
}
package ba.codingstoic.user

import android.content.SharedPreferences
import androidx.core.content.edit
import ba.codingstoic.data.GPodderPodcastSource

/**
 * Created by Abdurahman Adilovic on 3/28/20.
 */

class UserRepository(
    private val gPodderPodcastSource: GPodderPodcastSource,
    private val sharedPreferences: SharedPreferences
) {
    suspend fun loginUser() {
        val response = gPodderPodcastSource.login("")
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
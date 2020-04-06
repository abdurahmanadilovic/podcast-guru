package ba.codingstoic.user

import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * Created by Abdurahman Adilovic on 4/5/20.
 */

class CookieManager(private val sharedPreferences: SharedPreferences) {
    private val cookiePrefsKey = "cookie"
    private val cookieExpiresPrefsKey = "cookie-expires"

    fun storeCookie(cookie: String) {
        sharedPreferences.edit {
            putString(cookiePrefsKey, cookie)
        }
    }

    fun getCookie(): String {
        return sharedPreferences.getString(cookiePrefsKey, "") ?: ""
    }

    fun storeExpiresAt(date: String) {
        sharedPreferences.edit {
            putString(cookieExpiresPrefsKey, date)
        }
    }

    fun getExpiresAt(): String? {
        return sharedPreferences.getString(cookieExpiresPrefsKey, null)
    }
}
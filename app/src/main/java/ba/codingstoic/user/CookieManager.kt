package ba.codingstoic.user

import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * Created by Abdurahman Adilovic on 4/5/20.
 */

class CookieManager(private val sharedPreferences: SharedPreferences) {
    private val cookiePrefsKey = "cookie"

    fun storeCookie(cookie: String) {
        sharedPreferences.edit {
            putString(cookiePrefsKey, cookie)
        }
    }

    fun getCookie(): String {
        return sharedPreferences.getString(cookiePrefsKey, "") ?: ""
    }
}
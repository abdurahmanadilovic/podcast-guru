package ba.codingstoic.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by Abdurahman Adilovic on 4/26/20.
 */

fun Context.toastIt(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

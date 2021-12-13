package rx_activity_result2

import android.content.Intent
import java.io.Serializable

interface OnResult : Serializable {
    fun response(requestCode: Int, resultCode: Int, data: Intent?)
    fun error(throwable: Throwable?)
}
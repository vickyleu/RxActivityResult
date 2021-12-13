package rx_activity_result2

import android.content.Intent
import io.reactivex.rxjava3.core.Observable

interface OnPreResult<T> {
    fun response(requestCode: Int, resultCode: Int, data: Intent?): Observable<T>
}
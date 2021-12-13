package rx_activity_result2

import android.content.Intent
import rx_activity_result2.OnPreResult
import rx_activity_result2.OnResult
import android.app.Activity
import android.app.Application
import android.os.Bundle
import rx_activity_result2.HolderActivity
import rx_activity_result2.RequestIntentSender
import android.content.ActivityNotFoundException
import android.content.IntentSender.SendIntentException
import kotlin.Throws
import rx_activity_result2.ActivitiesLifecycleCallbacks
import rx_activity_result2.RxActivityResult
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlin.jvm.JvmOverloads
import android.content.IntentSender
import androidx.fragment.app.FragmentActivity
import kotlin.jvm.Volatile
import android.app.Application.ActivityLifecycleCallbacks
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Function
import java.lang.Exception
import java.util.concurrent.TimeUnit

class ActivitiesLifecycleCallbacks(val application: Application) {
    @Volatile
    var liveActivity: Activity? = null
    var activityLifecycleCallbacks: ActivityLifecycleCallbacks? = null
    private fun registerActivityLifeCycle() {
        if (activityLifecycleCallbacks != null) application.unregisterActivityLifecycleCallbacks(
            activityLifecycleCallbacks
        )
        activityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                liveActivity = activity
            }

            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {
                liveActivity = activity
            }

            override fun onActivityPaused(activity: Activity) {
                liveActivity = null
            }

            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        }
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }

    /**
     * Emits just one time a valid reference to the current activity
     * @return the current activity
     */
    @Volatile
    var emitted = false
    val oLiveActivity: Observable<Activity?>
        get() {
            emitted = false
            return Observable.interval(50, 50, TimeUnit.MILLISECONDS)
                .map { if (liveActivity == null) 0 else liveActivity }
                .takeWhile { candidate ->
                    var continueEmitting = true
                    if (emitted) continueEmitting = false
                    if (candidate is Activity) emitted = true
                    continueEmitting
                }
                .filter { candidate -> candidate is Activity }
                .map { activity -> activity as Activity }
        }

    init {
        registerActivityLifeCycle()
    }
}
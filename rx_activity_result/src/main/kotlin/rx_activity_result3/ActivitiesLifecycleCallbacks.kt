package rx_activity_result3

import android.app.Activity
import android.app.Application
import android.os.Bundle
import kotlin.jvm.Volatile
import android.app.Application.ActivityLifecycleCallbacks
import io.reactivex.rxjava3.core.Observable
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
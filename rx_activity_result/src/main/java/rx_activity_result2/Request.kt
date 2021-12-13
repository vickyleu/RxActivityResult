/*
 * Copyright 2016 Copyright 2016 Víctor Albertos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rx_activity_result2

import android.content.Intent
import rx_activity_result2.OnPreResult
import rx_activity_result2.OnResult
import android.app.Activity
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

open class Request(private val intent: Intent?) {
    private var onPreResult: OnPreResult<*>? = null
    private var onResult: OnResult? = null
    fun setOnPreResult(onPreResult: OnPreResult<*>?) {
        this.onPreResult = onPreResult
    }

    fun onPreResult(): OnPreResult<*>? {
        return onPreResult
    }

    fun setOnResult(onResult: OnResult?) {
        this.onResult = onResult
    }

    fun onResult(): OnResult? {
        return onResult
    }

    fun intent(): Intent? {
        return intent
    }
}
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
package rx_activity_result3

import android.content.Intent

class Result<T>(
    private val targetUI: T,
    private val requestCode: Int,
    private val resultCode: Int,
    private val data: Intent?
) {
    fun requestCode(): Int {
        return requestCode
    }

    fun resultCode(): Int {
        return resultCode
    }

    fun data(): Intent? {
        return data
    }

    fun targetUI(): T {
        return targetUI
    }
}
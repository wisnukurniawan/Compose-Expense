package com.wisnu.kurniawan.wallee.runtime.initializer

import android.content.Context
import androidx.startup.Initializer
import com.wisnu.kurniawan.coreLogger.ActivityLifecycleLoggr
import com.wisnu.kurniawan.wallee.runtime.WalleeApp

class ActivityLifecycleLoggrInitializer : Initializer<ActivityLifecycleLoggr> {
    override fun create(context: Context): ActivityLifecycleLoggr {
        return ActivityLifecycleLoggr().also {
            (context.applicationContext as WalleeApp)
                .registerActivityLifecycleCallbacks(it)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf(LoggrInitializer::class.java)
}

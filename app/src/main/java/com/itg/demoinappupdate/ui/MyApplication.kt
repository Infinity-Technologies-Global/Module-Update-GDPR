package com.itg.demoinappupdate.ui

import android.app.Application
import android.util.Log
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.LogLevel


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val environment = AdjustConfig.ENVIRONMENT_SANDBOX
        val config = AdjustConfig(this, "8bdlm1qio1hc", environment)

        // Change the log level.

        // Change the log level.
        config.setLogLevel(LogLevel.VERBOSE)
        config.setPreinstallTrackingEnabled(true)
        config.setOnAttributionChangedListener { attribution ->
            Log.d("TAG_ADJUST", "Attribution callback called!")
            Log.d("TAG_ADJUST", "Attribution: $attribution")
        }

        // Set event success tracking delegate.

        // Set event success tracking delegate.
        config.setOnEventTrackingSucceededListener { eventSuccessResponseData ->
            Log.d("TAG_ADJUST", "Event success callback called!")
            Log.d("TAG_ADJUST", "Event success data: $eventSuccessResponseData")
        }
        // Set event failure tracking delegate.
        // Set event failure tracking delegate.
        config.setOnEventTrackingFailedListener { eventFailureResponseData ->
            Log.d("TAG_ADJUST", "Event failure callback called!")
            Log.d("TAG_ADJUST", "Event failure data: $eventFailureResponseData")
        }

        // Set session success tracking delegate.

        // Set session success tracking delegate.
        config.setOnSessionTrackingSucceededListener { sessionSuccessResponseData ->
            Log.d("TAG_ADJUST", "Session success callback called!")
            Log.d("TAG_ADJUST", "Session success data: $sessionSuccessResponseData")
        }

        // Set session failure tracking delegate.

        // Set session failure tracking delegate.
        config.setOnSessionTrackingFailedListener { sessionFailureResponseData ->
            Log.d("TAG_ADJUST", "Session failure callback called!")
            Log.d("TAG_ADJUST", "Session failure data: $sessionFailureResponseData")
        }


        config.setSendInBackground(true)
        Adjust.onCreate(config)
    }
}
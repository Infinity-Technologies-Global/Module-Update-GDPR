package com.itg.iaumodule

import android.app.Activity
import com.google.android.ump.FormError

interface IAdConsentCallBack {
    fun activity(): Activity

    fun isDebug(): Boolean

    fun isUnderAgeAd(): Boolean

    fun onNotUsingAdConsent()

    fun onConsentSuccess()

    fun onConsentError(formError: FormError)
}
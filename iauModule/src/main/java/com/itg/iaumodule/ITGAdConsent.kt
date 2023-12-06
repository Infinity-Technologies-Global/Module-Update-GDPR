package com.itg.iaumodule

import android.content.Context
import android.telephony.TelephonyManager
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform

object ITGAdConsent {

    fun getCountryCode(context: Context): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.networkCountryIso.uppercase()
    }

    fun listEEACountry(): List<String> {
        return listOf(
            "AT", "BE", "BG", "HR", "CY", "CZ", "DK", "EE", "FI", "FR", "DE", "GR", "HU", "IE",
            "IT", "LV", "LT", "LU", "MT", "NL", "PL", "PT", "RO", "SK", "SI", "ES", "SE"
        )
    }

    fun listUKCountry(): List<String> {
        return listOf("GB", "GG", "IM", "JE")
    }

    fun listAdConsentCountry(): List<String> {
        return listEEACountry() + listUKCountry()
    }

    fun isAdConsentCountry(context: Context): Boolean {
        return listAdConsentCountry().contains(getCountryCode(context))
    }

    fun showConsent(context: Context,callback: IAdConsentCallBack) {
//        if (isAdConsentCountry(context)) {
//            setupConsent(callback)
//        } else {
//            callback.onNotUsingAdConsent()
//        }
        setupConsent(callback)

    }

    private fun setupConsent(callback: IAdConsentCallBack) {

        val consentInformation: ConsentInformation =
            UserMessagingPlatform.getConsentInformation(callback.activity())

        // Set tag for underage of consent. false means users are not underage.
        val params = if (callback.isDebug()) {

            val debugSettings = ConsentDebugSettings.Builder(callback.activity())
                .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
                .addTestDeviceHashedId("ED3576D8FCF2F8C52AD8E98B4CFA4005")
                .setForceTesting(true)
                .build()

            ConsentRequestParameters.Builder()
                .setTagForUnderAgeOfConsent(callback.isUnderAgeAd())
                .setConsentDebugSettings(debugSettings)
                .build()

        } else {
            ConsentRequestParameters.Builder()
                .setTagForUnderAgeOfConsent(callback.isUnderAgeAd())
                .build()
        }

        consentInformation.requestConsentInfoUpdate(callback.activity(), params,
            {
                // The consent information state was updated.
                // You are now ready to check if a form is available.
                if (consentInformation.isConsentFormAvailable) {
                    loadForm(consentInformation, callback)
                } else {
                    callback.onNotUsingAdConsent()
                }
            },
            { formError ->
                // Handle the error.
                callback.onConsentError(formError)
            })
    }

    private fun loadForm(consentInformation: ConsentInformation, callback: IAdConsentCallBack) {
        // Loads a consent form. Must be called on the main thread.
        UserMessagingPlatform.loadConsentForm(callback.activity(), { consentForm ->
            if (consentInformation.consentStatus == ConsentInformation.ConsentStatus.REQUIRED) {
                consentForm.show(callback.activity()) { formError ->
                    if (consentInformation.consentStatus == ConsentInformation.ConsentStatus.OBTAINED) {
                        // App can start requesting ads.
                        callback.onConsentSuccess()
                    }

                    // Handle dismissal by reloading form.
                    loadForm(consentInformation, callback)
                }
            } else if (consentInformation.consentStatus == ConsentInformation.ConsentStatus.NOT_REQUIRED) {
                callback.onNotUsingAdConsent()
            }
        }, { formError ->
            // Handle the error.
            callback.onConsentError(formError)
        })
    }

}
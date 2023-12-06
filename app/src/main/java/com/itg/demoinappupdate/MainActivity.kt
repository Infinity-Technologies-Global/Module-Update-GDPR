package com.itg.demoinappupdate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.itg.demoinappupdate.databinding.ActivityMainBinding
import com.itg.iaumodule.IUpdateInstanceCallback
import com.itg.iaumodule.InAppUpdateManager


class MainActivity : AppCompatActivity() {

    private val isShowDialogUpdate: Boolean = true
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonCheckGgFlex.setOnClickListener {
            updateWithType(AppUpdateType.FLEXIBLE, isShowDialogUpdate)
        }
        binding.buttonCheckUpdateGgImmediate.setOnClickListener {
            updateWithType(AppUpdateType.IMMEDIATE, isShowDialogUpdate)
        }
        binding.buttonShowConsent.setOnClickListener {
            InAppUpdateManager(this, 100, object : IUpdateInstanceCallback {
                override fun initializeMobileAdsSdk() {

                }

                override fun resultConsentForm(canRequestAds: Boolean) {

                }

            }).checkBelowGeoEEA(true)
        }
    }

//    update for all = true // update all users if false check next step below
//    only GDPR = true // update  for EEA geo if false
//    if user close the dialog update ( show again after 15s ) in MainActivity

    private fun updateWithType(type: Int, isShowDialogUpdate: Boolean) {
        if (isShowDialogUpdate) InAppUpdateManager(this, 100, object : IUpdateInstanceCallback {
            override fun updateAvailableListener(updateAvailability: AppUpdateInfo): Int {
                when (updateAvailability.updateAvailability()) {
                    UpdateAvailability.UPDATE_AVAILABLE -> {
                        // Need to show Dialog check option then return type
                        return type
                    }
                }
                return AppUpdateType.FLEXIBLE
            }

        }).checkUpdateAvailable()

//        ITGAdConsent.showConsent(this, object : IAdConsentCallBack {
//            override fun activity(): Activity {
//                return this@MainActivity
//            }
//
//            override fun isDebug(): Boolean {
//                return true
//            }
//
//            override fun isUnderAgeAd(): Boolean {
//                return false
//            }
//
//            override fun onNotUsingAdConsent() {
//                Log.v("InAppUpdateManager", "onNotUsingAdConsent ")
//            }
//
//            override fun onConsentSuccess() {
//                Log.v("InAppUpdateManager", "onConsentSuccess :Success")
//            }
//
//            override fun onConsentError(formError: FormError) {
//                Log.v("InAppUpdateManager", "onConsentError :formError ${formError.message}")
//
//            }
//        })
//        startActivity(Intent(this, LanguageActivity::class.java))
    }
}
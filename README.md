
# ITG Module Update + Consent
## How to get a Git project into your build: [![](https://jitpack.io/v/Infinity-Technologies-Global/Module-Update-GDPR.svg)](https://jitpack.io/#Infinity-Technologies-Global/Module-Update-GDPR)

**Step 1.**  Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```
	maven { url 'https://jitpack.io' }
		
```
**Step 2.**  Add the dependency
```
	dependencies {
	        implementation 'com.github.Infinity-Technologies-Global:Module-Update-GDPR:version'
	}
```


## How to use for Update
#### Use class ITGUpdateManager for update in app
You can use AppUpdateType.FLEXIBLE or AppUpdateType.IMMEDIATE for type of dialog

```
	//isShowDialogUpdate get from RemoteConfig or from your request


	if (isShowDialogUpdate) ITGUpdateManager(this, REQUEST_CODE, object : IUpdateInstanceCallback {  
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
```


## How to use load and show Consent Dialog Admob
- For load Consent you use
```
	ITGAdConsent.loadConsent(this) // param is IAdConsentCallBack
```
- For show Consent after load ( you can show in other Activity with Activity load before)
```
	ITGAdConsent.showDialogConsent(this)
```
- For load And show
```
	ITGAdConsent.loadAndShowConsent(this) // load and show in Activity
```
- For ResetDialog
```
	ITGAdConsent.resetConsentDialog()
```

- About Callback
```
interface IAdConsentCallBack {  
    fun getCurrentActivity(): Activity 
		// Return current Activity
    fun isDebug(): Boolean  
	    // Return is mode debug or release ( need use BuildConfig ) maybe missing when release application 
    fun isUnderAgeAd(): Boolean  
	    // Set tag for underage of consent. false means users are not underage.
	    // ReadMore : https://developers.google.com/admob/android/targeting
	    
    fun onNotUsingAdConsent()  
	    // Meaning your country not require Ad Consent
	
	fun onConsentSuccess(canPersonalized: Boolean) {  
	    // return when user click option in Dialog Consent
	    // canPersonalized mean user click Consent or Do not Consent
	    // Can tracking in this function
    }
    
    fun onConsentError(formError: FormError)  
	    //Load Consent Form error
    fun onLoadConsentSuccess()  
	    // Meaning Load Consent Form Success

}
```


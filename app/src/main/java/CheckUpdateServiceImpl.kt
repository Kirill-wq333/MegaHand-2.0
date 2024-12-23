import android.app.Activity
import com.evothings.mhand.presentation.feature.splash.viewmodel.checkUpdate.CheckUpdateService
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.UpdateAvailability

class CheckUpdateServiceImpl(private val activity: Activity) : CheckUpdateService {

    override fun checkIsUpdateAvailable(callback: (Boolean) -> Unit) {
        AppUpdateManagerFactory.create(activity.applicationContext).appUpdateInfo
            .addOnSuccessListener {
                if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
            .addOnFailureListener {
                callback(false)
            }
    }

}
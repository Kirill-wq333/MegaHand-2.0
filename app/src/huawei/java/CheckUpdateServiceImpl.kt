import android.app.Activity
import android.content.Intent
import android.os.Build
import android.util.Log
import com.evothings.mhand.presentation.feature.splash.viewmodel.checkUpdate.CheckUpdateService
import com.huawei.hms.jos.JosApps
import com.huawei.updatesdk.service.appmgr.bean.ApkUpgradeInfo
import com.huawei.updatesdk.service.otaupdate.CheckUpdateCallBack
import com.huawei.updatesdk.service.otaupdate.UpdateKey
import java.io.Serializable

class CheckUpdateServiceImpl(private val activity: Activity) : CheckUpdateService {

    override fun checkIsUpdateAvailable(callback: (Boolean) -> Unit) {
        val client = JosApps.getAppUpdateClient(activity)
        try {
            client.checkAppUpdate(
                activity.applicationContext,
                object : CheckUpdateCallBack {

                    override fun onUpdateInfo(updateIntent: Intent?) {
                        updateIntent?.let {
                            val updateBundle = it.serializable<ApkUpgradeInfo>(UpdateKey.INFO)
                            callback(updateBundle != null)
                        }
                    }

                    override fun onMarketInstallInfo(p0: Intent?) = callback(false)

                    override fun onMarketStoreError(p0: Int) = callback(false)

                    override fun onUpdateStoreError(p0: Int) = callback(false)
                }
            )
        } catch(e: Exception) {
            Log.e("UpdateServiceImpl", e.message.toString())
            callback(false)
        }
    }

    private inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
    }

}
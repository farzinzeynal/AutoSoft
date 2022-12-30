package az.avtomatika.autosoft.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import az.avtomatika.autosoft.base.BaseActivity
import az.avtomatika.autosoft.databinding.ActivitySplashScreenBinding
import az.avtomatika.autosoft.model.remote.request.CheckVersionRequest
import az.avtomatika.autosoft.model.remote.response.CheckVersionResponse
import az.avtomatika.autosoft.ui.unregister.UnregisterActivity
import az.avtomatika.autosoft.util.HttpMethods
import az.avtomatika.autosoft.util.NetworkResult
import az.avtomatika.autosoft.util.PopupAnimTypes
import az.avtomatika.autosoft.util.core.MainPopupDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivitySplashScreenBinding =
        ActivitySplashScreenBinding::inflate

    private val viewModel: SplashVeiwModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAppVersion()
        initApi()


    }

    fun checkVersion(response: CheckVersionResponse?) {
        val intn = Intent(this, UnregisterActivity::class.java)
        startActivity(intn)
        finish()
    }

    private fun checkAppVersion() {
        val checkVersionRequest = CheckVersionRequest(
            appversion = "1.0",
            method = HttpMethods.checkVersion
        )
        viewModel.checkVersion(checkVersionRequest)
    }


    private fun initApi() {
        viewModel.checkVersionLiveData.observe(this) {
            when (it) {
                is NetworkResult.Success -> {
                    checkVersion(it.response)
                }
                is NetworkResult.Error -> {
                    it.message?.let { message -> showErrorMessage(message) }
                }
                is NetworkResult.Loading -> {
                }
            }
        }
    }

    private fun showErrorMessage(errorMessage: String) {
        MainPopupDialog.infoAlert(
            this,
            MainPopupDialog.InfoDatas("Diqqət", errorMessage),
            animType = PopupAnimTypes.ERROR
        )
    }


}
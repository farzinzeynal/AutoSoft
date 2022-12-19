package az.avtomatika.autosoft.ui.splash

import android.annotation.SuppressLint
import android.view.LayoutInflater
import az.avtomatika.autosoft.base.BaseActivity
import az.avtomatika.autosoft.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivitySplashScreenBinding = ActivitySplashScreenBinding::inflate



}
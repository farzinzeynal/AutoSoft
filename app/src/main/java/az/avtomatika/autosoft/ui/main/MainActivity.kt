package az.avtomatika.autosoft.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import az.avtomatika.autosoft.R
import az.avtomatika.autosoft.base.BaseActivity
import az.avtomatika.autosoft.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navView: BottomNavigationView = views.navView

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }
}
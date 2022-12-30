package az.avtomatika.autosoft.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import az.avtomatika.autosoft.R
import az.avtomatika.autosoft.base.BaseActivity
import az.avtomatika.autosoft.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = Color.WHITE




    }








}


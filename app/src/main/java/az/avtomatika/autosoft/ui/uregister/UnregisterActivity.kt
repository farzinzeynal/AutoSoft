package az.avtomatika.autosoft.ui.uregister

import android.os.Bundle
import android.view.LayoutInflater
import az.avtomatika.autosoft.R
import az.avtomatika.autosoft.base.BaseActivity
import az.avtomatika.autosoft.databinding.ActivityUnregisterBinding

class UnregisterActivity : BaseActivity<ActivityUnregisterBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityUnregisterBinding = ActivityUnregisterBinding::inflate


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(R.id.unregisterContainer,LoginFragment()).commit()
    }


}
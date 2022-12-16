package az.avtomatika.autosoft.ui.uregister

import android.content.Intent
import android.os.Bundle
import android.view.View
import az.avtomatika.autosoft.base.BaseFragment
import az.avtomatika.autosoft.databinding.FragmentLoginBinding
import az.avtomatika.autosoft.ui.main.face_matching.FaceMatchingActivity
import az.avtomatika.autosoft.util.Constants.FACE_MATCHING_REQUEST_CODE
import az.avtomatika.autosoft.util.core.MainPopupDialog

class LoginFragment: BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()



        views.btnLogin.setOnClickListener {
            val intn = Intent(requireActivity(), FaceMatchingActivity::class.java)
            startActivityForResult(intn, FACE_MATCHING_REQUEST_CODE)
        }




    }

    private fun initViews() {
        views.inputPassword.setInputTypePassWord()
        views.inputPassword.setHint("Password")
        views.inputUsername.setHint("Username")
    }



}
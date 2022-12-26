package az.avtomatika.autosoft.ui.unregister.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import az.avtomatika.autosoft.BuildConfig
import az.avtomatika.autosoft.R
import az.avtomatika.autosoft.base.BaseFragment
import az.avtomatika.autosoft.databinding.FragmentLoginBinding
import az.avtomatika.autosoft.model.remote.response.LoginResponseModel
import az.avtomatika.autosoft.ui.main.MainActivity
import az.avtomatika.autosoft.ui.main.face_matching.FaceMatchingActivity
import az.avtomatika.autosoft.util.Constants
import az.avtomatika.autosoft.util.Constants.FACE_MATCHING_REQUEST_CODE
import az.avtomatika.autosoft.util.NetworkResult
import az.avtomatika.autosoft.util.PopupAnimTypes
import az.avtomatika.autosoft.util.core.MainPopupDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate),
    View.OnClickListener {

    private val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initApi()


    }

    private fun loginUser(username: String, password: String) {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            viewModel.loginUser(views.inputUsername.getText(), views.inputPassword.getText())
        } else {
            MainPopupDialog.infoAlert(
                requireContext(),
                MainPopupDialog.InfoDatas("Diqqət", "Məlumatlar daxil edilməyib"),
                animType = PopupAnimTypes.WARNING
            )
        }
    }

    private fun initViews() {
        views.btnLogin.setOnClickListener(this)
        views.inputPassword.setInputTypePassWord()
        views.inputPassword.setHint("Password")
        views.inputUsername.setHint("Username")
        views.textVersionName.text = BuildConfig.VERSION_NAME


    }

    private fun startFaceMatching() {
        val intn = Intent(requireActivity(), FaceMatchingActivity::class.java)
        startActivityForResult(intn, FACE_MATCHING_REQUEST_CODE)
    }

    private fun startFaceEnrolling() {
        //navigate to enroll fragment
    }

    private fun initApi() {
        viewModel.loginResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    checkDatas(it.data)
                }
                is NetworkResult.Error -> {
                    MainPopupDialog.infoAlert(
                        requireContext(),
                        MainPopupDialog.InfoDatas("Diqqət", it.message.toString()),
                        animType = PopupAnimTypes.ERROR)
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }

    private fun checkDatas(data: LoginResponseModel?) {
        if (data?.imageBase64.isNullOrEmpty()) {
            startFaceEnrolling()
        } else {
            Constants.USER_IMAGE = data?.imageBase64 ?: ""
            startFaceMatching()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == FACE_MATCHING_REQUEST_CODE) {
            if (data?.hasExtra("isAuthenticated") == true) {
                val isAuth = data.getBooleanExtra("isAuthenticated", false)
                checkAuth(isAuth)
            }
        }

    }

    private fun checkAuth(isAuth: Boolean) {
        if (isAuth) {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.btnLogin -> {
                loginUser(views.inputUsername.getText(), views.inputPassword.getText())
                //loginUser("farzin","12345")
            }
        }
    }

}

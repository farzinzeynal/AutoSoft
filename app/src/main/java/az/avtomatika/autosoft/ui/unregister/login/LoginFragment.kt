package az.avtomatika.autosoft.ui.unregister.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import az.avtomatika.autosoft.BuildConfig
import az.avtomatika.autosoft.R
import az.avtomatika.autosoft.base.BaseActivity
import androidx.navigation.fragment.findNavController
import az.avtomatika.autosoft.base.BaseFragment
import az.avtomatika.autosoft.databinding.FragmentLoginBinding
import az.avtomatika.autosoft.model.remote.request.LoginRequest
import az.avtomatika.autosoft.model.remote.request.ProfileInfoRequest
import az.avtomatika.autosoft.model.remote.response.LoginResponseModel
import az.avtomatika.autosoft.model.remote.response.ProfileData
import az.avtomatika.autosoft.model.remote.response.ProfileInfoResponse
import az.avtomatika.autosoft.ui.main.MainActivity
import az.avtomatika.autosoft.ui.main.face_matching.FaceMatchingActivity
import az.avtomatika.autosoft.util.*
import az.avtomatika.autosoft.util.Constants.FACE_MATCHING_REQUEST_CODE
import az.avtomatika.autosoft.util.UtilFunctions.getNavOptions
import az.avtomatika.autosoft.util.UtilFunctions.getNavOptionsDisableBack
import az.avtomatika.autosoft.util.core.MainPopupDialog
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate),
    View.OnClickListener {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initLoginApi()
        initProfileDataApi()


    }


    private fun loginUser(username: String, password: String) {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            if (username.length < 7) {
                views.inputUsername.setError("Mininum 7 simvol olmalıdır!")
            } else {
                viewModel.loginUser(username, password)
            }
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
        views.inputPassword.setHint("FIN kod")
        views.inputUsername.setHint("Parol")
        views.inputUsername.setText("FIN1234")
        views.inputPassword.setText("123456")
        views.textVersionName.text = BuildConfig.VERSION_NAME


    }

    private fun startFaceMatching() {
        val intn = Intent(requireActivity(), FaceMatchingActivity::class.java)
        startActivityForResult(intn, FACE_MATCHING_REQUEST_CODE)
    }

    private fun startFaceEnrolling() {
        MainPopupDialog.infoAlert(
            requireContext(),
            MainPopupDialog.InfoDatas(
                "Diqqət",
                "Biometrik melumatlarınız tapılmadı. Məlumatları daxil etmak üçün nöbbəti səhifəyə keçin"
            ),
            object : MainPopupDialog.InfoPopUpDismissListener {
                override fun onDismiss() {
                     Navigation.findNavController(requireView()).navigate(R.id.faceEnrollFragment,null, getNavOptionsDisableBack(requireView()))
                }
            }, animType = PopupAnimTypes.WARNING
        )
        //navigate to enroll fragment
    }


    private fun initProfileDataApi() {
        viewModel.profileInfoLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    if (it.response?.data != null) {
                        saveDatas(it.response)
                    } else {
                        MainPopupDialog.infoAlert(
                            requireContext(),
                            MainPopupDialog.InfoDatas("Diqqət", it.response?.error?.message ?: ""),
                            animType = PopupAnimTypes.ERROR
                        )
                    }
                }
                is NetworkResult.Error -> {
                    MainPopupDialog.infoAlert(
                        requireContext(),
                        MainPopupDialog.InfoDatas("Diqqət", it.message.toString()),
                        animType = PopupAnimTypes.ERROR
                    )
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }


    private fun initLoginApi() {
        viewModel.loginResponse.observe(viewLifecycleOwner) {
            BaseActivity.loadingDown()
            when (it) {
                is NetworkResult.Success -> {
                    if (it.response?.data != null) {
                        saveToken(it.response)
                        Handler().postDelayed({
                            getProfileData()
                        }, 100)
                    } else {
                        MainPopupDialog.infoAlert(
                            requireContext(),
                            MainPopupDialog.InfoDatas("Diqqət", it.response?.error?.message ?: ""),
                            animType = PopupAnimTypes.ERROR
                        )
                    }
                }
                is NetworkResult.Error -> {
                    MainPopupDialog.infoAlert(
                        requireContext(),
                        MainPopupDialog.InfoDatas("Diqqət", it.message.toString()),
                        animType = PopupAnimTypes.ERROR
                    )
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }


    private fun saveDatas(response: ProfileInfoResponse?) {
        viewModel.saveDatas(response)
        if (response?.data?.avatar != null) {
            /*  try{
                  val url = URL(response.data.avatar.url)
                  USER_IMAGE_BITMAP = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                  USER_IMAGE_BITMAP
              }catch (ex:Exception){}
              startFaceMatching()*/
            startFaceEnrolling()
        } else {
            startFaceEnrolling()
        }
    }

    private fun getProfileData() {
        viewModel.getProfileDatas(views.inputUsername.getText())
    }

    private fun saveToken(response: LoginResponseModel?) {
        MainShared(requireContext(), SharedTypes.AUTH).setSharedData(
            SharedPrefNames.SESS_TOKEN,
            response?.data?.sesstoken
        )
        Constants.sessToken = response?.data?.sesstoken ?: ""
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
            }
        }
    }

}

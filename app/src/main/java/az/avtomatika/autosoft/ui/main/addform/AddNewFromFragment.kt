package az.avtomatika.autosoft.ui.main.addform

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import az.avtomatika.autosoft.R
import az.avtomatika.autosoft.base.BaseFragment
import az.avtomatika.autosoft.databinding.FragmentAddNewFormBinding
import az.avtomatika.autosoft.ui.main.face_matching.FaceMatchingActivity
import az.avtomatika.autosoft.util.Constants
import az.avtomatika.autosoft.util.Constants.FACE_MATCHING_REQUEST_CODE
import az.avtomatika.autosoft.util.Constants.LOCATION_SOURCE_SETTINGS_CODE
import az.avtomatika.autosoft.util.NetworkResult
import az.avtomatika.autosoft.util.PopupAnimTypes
import az.avtomatika.autosoft.util.UtilFunctions
import az.avtomatika.autosoft.util.UtilFunctions.encodeBitmapToBase64
import az.avtomatika.autosoft.util.core.MainPopupDialog
import az.avtomatika.autosoft.util.helper.CameraHelper
import az.avtomatika.autosoft.util.helper.LocationHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddNewFromFragment :
    BaseFragment<FragmentAddNewFormBinding>(FragmentAddNewFormBinding::inflate),
    View.OnClickListener {

    private var latitude = ""
    private var longitude = ""
    private var shiftType = ""
    private var imageToSend: Bitmap? = null

    private val viewModel: FormViewModel by viewModel()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initInsertShiftApi()
        checPermissions()

        shiftType = if (Constants.currentShifType == "1") "2" else "1"
        /*arguments?.let {
            shiftType = it.getString("shiftType") ?: ""
        }*/
    }

    private fun initInsertShiftApi() {
        viewModel.inserNewShiftLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    if (it.response?.data != null) {
                        showSucces()
                    } else {
                        MainPopupDialog.infoAlert(
                            requireContext(),
                            MainPopupDialog.InfoDatas("Diqq??t", it.response?.error?.message ?: ""),
                            animType = PopupAnimTypes.ERROR
                        )
                    }
                }
                is NetworkResult.Error -> {
                    MainPopupDialog.infoAlert(
                        requireContext(),
                        MainPopupDialog.InfoDatas("Diqq??t", it.message.toString()),
                        animType = PopupAnimTypes.ERROR
                    )
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }

    private fun showSucces() {
        MainPopupDialog.infoAlert(
            requireContext(),
            MainPopupDialog.InfoDatas("U??urlu ??m??liyyat", "Form u??urla g??nd??rildi"),
            object : MainPopupDialog.InfoPopUpDismissListener {
                override fun onDismiss() {
                    requireActivity().onBackPressed()
                }
            },
            animType = PopupAnimTypes.SUCCES
        )
    }

    private fun initViews() {
        views.buttonRetry.setOnClickListener(this)
        views.btnSendForm.setOnClickListener(this)
        views.addImageLayout.setOnClickListener(this)
    }


    private fun checkButtonEnabled() {
        if (imageToSend != null) {
            views.btnSendForm.isEnabled = true
            views.btnSendForm.alpha = 1.0F
        } else {
            views.btnSendForm.isEnabled = false
            views.btnSendForm.alpha = 0.5F
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                getCurrentLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                getCurrentLocation()
            }
            else -> {
                showPermissionWarning()
            }
        }
    }

    private fun showPermissionWarning() {
        MainPopupDialog.infoAlert(
            requireContext(),
            MainPopupDialog.InfoDatas(
                "U??urlu ??m??liyyat",
                "Form g??nd??rm??k ??????n m??kan m??lumatlar??n?? al??nmas??na icaz?? verin!"
            ), object : MainPopupDialog.InfoPopUpDismissListener {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun onDismiss() {
                }
            },
            animType = PopupAnimTypes.WARNING
        )
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun checPermissions() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    /*  @RequiresApi(Build.VERSION_CODES.S)
      private var requestPermissionLauncher = registerForActivityResult(
          ActivityResultContracts.RequestPermission()
      ) { isGranted ->
          if (isGranted) {
            getCurrentLocation()
          } else {
              MainPopupDialog.infoAlert(
                  requireContext(),
                  MainPopupDialog.InfoDatas("Diqq??t", "Form g??nd??rm??k ??????n m??kan m??lumatlar??n?? al??nmas??na icaz?? verin!"),
                  object : MainPopupDialog.InfoPopUpDismissListener {
                      override fun onDismiss() {
                          LocationHelper.requestPermissions(requireActivity())
                      }
                  },
                  animType = PopupAnimTypes.WARNING
              )
          }
      }*/


    private var cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val photoBitmap = result.data?.getExtras()?.get("data") as Bitmap
                imageToSend = photoBitmap
                views.buttonRetry.visibility = View.VISIBLE
                checkButtonEnabled()
                val uri =
                    UtilFunctions.bitmapToFile(requireActivity().applicationContext, photoBitmap)
                views.capturedImage.setImageBitmap(photoBitmap)
            }
        }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.S)
    private fun getCurrentLocation() {
        if (LocationHelper.checkPermissions(requireActivity())) {
            if (LocationHelper.isLocationEnabled(requireActivity())) {
                LocationHelper.getUserCurrentLocation(requireActivity()) { location ->
                    views.locationText.text = location.address
                    latitude = location.latitude
                    longitude = location.longitude
                }

            } else {
                Toast.makeText(
                    requireContext(),
                    "Z??hm??t olmasa GPS-i aktiv edin",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivityForResult(intent, LOCATION_SOURCE_SETTINGS_CODE)
            }
        } else {
            LocationHelper.requestPermissions(requireActivity())
        }
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.buttonRetry -> {
                val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraLauncher.launch(takePicture)
            }

            R.id.btnSendForm -> {
                checkDatas()
            }

            R.id.addImageLayout -> {
                startCapture()
            }

        }
    }

    private fun checkDatas() {
        if (latitude.isEmpty() && longitude.isEmpty()) {
            showError("Mekan melumatlar?? al??na bilm??di !")
        } else {
            startMatching()
        }
    }

    private fun showError(message: String) {
        MainPopupDialog.infoAlert(
            requireContext(),
            MainPopupDialog.InfoDatas("Diqq??t", message),
            animType = PopupAnimTypes.WARNING
        )
    }

    private fun startMatching() {
        val intn = Intent(requireActivity(), FaceMatchingActivity::class.java)
        startActivityForResult(intn, FACE_MATCHING_REQUEST_CODE)
    }

    private fun startCapture() {
        if (CameraHelper.checkCameraPermissions(requireActivity())) {
            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(takePicture)
        } else {
            CameraHelper.requestCameraPermissions(requireActivity())
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK) {

            when (requestCode) {
                FACE_MATCHING_REQUEST_CODE -> {
                    if (data?.hasExtra("isAuthenticated") == true) {
                        val isAuth = data.getBooleanExtra("isAuthenticated", false)
                        checkAuth(isAuth)
                    }
                }

                LOCATION_SOURCE_SETTINGS_CODE -> {
                    getCurrentLocation()
                }
            }
        }


    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun checkAuth(isAuth: Boolean) {
        if (isAuth) {
            sendForm(latitude, longitude, imageToSend)
        } else {
            showError("Biometrik m??lumatlar do??rulanmad?? !")
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun sendForm(latitude: String, longitude: String, imageToSend: Bitmap?) {
        val imageString =
            imageToSend?.let { encodeBitmapToBase64(it, Bitmap.CompressFormat.JPEG, 100) } ?: ""

        viewModel.insertNewShift(latitude, longitude, imageString, shiftType)
    }

    override fun onPause() {
        super.onPause()

    }


}
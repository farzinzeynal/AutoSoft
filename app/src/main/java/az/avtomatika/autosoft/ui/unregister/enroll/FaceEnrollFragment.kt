package az.avtomatika.autosoft.ui.unregister.enroll

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import az.avtomatika.autosoft.R
import az.avtomatika.autosoft.base.BaseFragment
import az.avtomatika.autosoft.databinding.FragmentFaceEnrollBinding
import az.avtomatika.autosoft.ui.unregister.login.LoginViewModel
import az.avtomatika.autosoft.util.NetworkResult
import az.avtomatika.autosoft.util.PopupAnimTypes
import az.avtomatika.autosoft.util.UtilFunctions
import az.avtomatika.autosoft.util.UtilFunctions.bitmapToFile
import az.avtomatika.autosoft.util.UtilFunctions.encodeBitmapToBase64
import az.avtomatika.autosoft.util.UtilFunctions.getNavOptions
import az.avtomatika.autosoft.util.UtilFunctions.getNavOptionsDisableBack
import az.avtomatika.autosoft.util.core.MainPopupDialog
import az.avtomatika.autosoft.util.helper.CameraHelper
import az.avtomatika.autosoft.util.helper.LocationHelper
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class FaceEnrollFragment :
    BaseFragment<FragmentFaceEnrollBinding>(FragmentFaceEnrollBinding::inflate),View.OnClickListener {


    private var capturedImage: Uri? = null
    private var capturedImageBitmap: Bitmap? = null
    private val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initUpdateAvatarApi()



    }


    private fun initViews() {
        views.btnFinishEnroll.setOnClickListener(this)
        views.btnStartCapture.setOnClickListener(this)

    }

    private fun initUpdateAvatarApi() {
        viewModel.updateAvatarLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    if (it.response?.data != null) {
                        showSucces()
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

    private fun showSucces() {
        MainPopupDialog.infoAlert(
            requireContext(),
            MainPopupDialog.InfoDatas("Uğurlu əməliyyat", "Şəkil uğurla göndərildi"), object : MainPopupDialog.InfoPopUpDismissListener {
                override fun onDismiss() {
                    Navigation.findNavController(requireView()).navigate(R.id.loginFragment,null, getNavOptionsDisableBack(requireView()))
                }
            },
            animType = PopupAnimTypes.SUCCES
        )
    }

    private var cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val photoBitmap = result.data?.getExtras()?.get("data") as Bitmap
                val uri = bitmapToFile(requireActivity().applicationContext,photoBitmap)
                capturedImageBitmap = photoBitmap
                capturedImage = uri
                views.imgAddPhoto.setImageBitmap(photoBitmap)
                views.btnStartCapture.text = "Yenidən çək"
                views.btnFinishEnroll.visibility=View.VISIBLE
            }
        }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btnStartCapture -> {
                startCapture()
            }

            R.id.btnFinishEnroll -> {
                sendPhotoToServer()
            }
        }
    }

    private fun sendPhotoToServer() {
        if (capturedImageBitmap!=null){
            val imageString = encodeBitmapToBase64(capturedImageBitmap!!,Bitmap.CompressFormat.JPEG,100)
            viewModel.updateProfileAvatar(imageString)
        }
    }

    private fun startCapture() {
        if (CameraHelper.checkCameraPermissions(requireActivity())) {
            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(takePicture)
        }
        else{
            CameraHelper.requestCameraPermissions(requireActivity())
        }
    }

    @Deprecated("Deprecated in Java")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CameraHelper.CAMERA_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraLauncher.launch(takePicture)
            }
        }
    }

}
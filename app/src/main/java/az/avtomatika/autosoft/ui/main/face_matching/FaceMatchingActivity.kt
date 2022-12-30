package az.avtomatika.autosoft.ui.main.face_matching

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import az.avtomatika.autosoft.R
import az.avtomatika.autosoft.base.BaseActivity
import az.avtomatika.autosoft.databinding.ActivityFaceMatchingBinding
import az.avtomatika.autosoft.util.Constants.USER_IMAGE
import az.avtomatika.autosoft.util.Constants.USER_IMAGE_BITMAP
import az.avtomatika.autosoft.util.FaceOperationTypes
import az.avtomatika.autosoft.util.UtilFunctions.decodeBase64
import com.regula.facesdk.FaceSDK
import com.regula.facesdk.configuration.FaceCaptureConfiguration
import com.regula.facesdk.enums.ImageType
import com.regula.facesdk.model.Image
import com.regula.facesdk.model.MatchFacesImage
import com.regula.facesdk.model.results.FaceCaptureResponse
import com.regula.facesdk.model.results.matchfaces.MatchFacesResponse
import com.regula.facesdk.model.results.matchfaces.MatchFacesSimilarityThresholdSplit
import com.regula.facesdk.request.MatchFacesRequest

class FaceMatchingActivity : BaseActivity<ActivityFaceMatchingBinding>(), View.OnClickListener {

    private var isFaceAuthenticated = false
    override val bindingInflater: (LayoutInflater) -> ActivityFaceMatchingBinding = ActivityFaceMatchingBinding::inflate

    private var userImageBitmap: Bitmap? = null
    private var captureBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        getCurrentImage()




    }

    private fun getCurrentImage() {
        userImageBitmap = USER_IMAGE_BITMAP

    }

    private fun initViews() {
        views.btnStartCapture.setOnClickListener(this)
        views.btnFinishMatch.setOnClickListener(this)
    }


    private fun startFaceCaptureActivity() {
        val configuration = FaceCaptureConfiguration.Builder().setCameraSwitchEnabled(true).build()

        FaceSDK.Instance().presentFaceCaptureActivity(this, configuration) {
            faceCaptureResponse: FaceCaptureResponse? ->
            if (faceCaptureResponse?.image != null) {
                startMatching(faceCaptureResponse.image)
            }
        }
    }


    private fun startMatching(image: Image?) {
        captureBitmap = image?.bitmap
        if (userImageBitmap!=null && captureBitmap!=null){
            loadingUp()
            views.infoText.text = "Biometrik melumatlar yoxlanılır...\n \nZəhmət olmasa gözləyin"
            Log.i("startMatch", "OK")
            views.faceAnimationView.visibility = View.GONE
            val firstImage = MatchFacesImage(userImageBitmap, ImageType.PRINTED, true)
            val secondImage = MatchFacesImage(captureBitmap,  ImageType.LIVE, true)
            val matchFacesRequest = MatchFacesRequest(arrayListOf(firstImage, secondImage))
            FaceSDK.Instance().matchFaces(matchFacesRequest) { matchFacesResponse: MatchFacesResponse ->
                val split = MatchFacesSimilarityThresholdSplit(matchFacesResponse.results, 0.75)
                if (split.matchedFaces.size > 0) {
                    loadingDown(FaceOperationTypes.MATCH_SUCCESS)
                    views.btnStartCapture.visibility = View.GONE
                    views.btnFinishMatch.visibility = View.VISIBLE
                    val similarity = split.matchedFaces[0].similarity
                    val similarityPercent = String.format("%.2f", similarity * 100)
                    if (similarity>0.90){
                        isFaceAuthenticated = true
                    }
                    Log.i("similarityPercent :", similarity.toString())
                    views.infoText.text = "Məlumatlar doğrulandı\n \n Oxşarlıq dərəcəsi: $similarityPercent%"
                } else {
                    loadingDown(FaceOperationTypes.MATCH_ERROR)
                    Log.i("matchError :", "OK")
                    views.btnStartCapture.text = "Bir daha cəhd edin"
                    views.btnFinishMatch.visibility = View.VISIBLE
                    views.infoText.text = "Biomentrik məlumat tapılmadı"
                }
            }
        }

    }



    private fun loadingDown(type: FaceOperationTypes){
        BaseActivity.loadingDown()
        views.faceAnimationView.visibility = View.VISIBLE


        if (type==FaceOperationTypes.MATCH_SUCCESS){
            views.faceAnimationView.setAnimation(R.raw.icon_success_anim)
            views.faceAnimationView.playAnimation()
            views.faceAnimationView.repeatCount =0
        }
        else{
            views.faceAnimationView.setAnimation(R.raw.icon_failed_anim)
            views.faceAnimationView.playAnimation()
            views.faceAnimationView.repeatCount =0
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){

            R.id.btnStartCapture ->{
                startFaceCaptureActivity()
            }

            R.id.btnFinishMatch ->{
                finisProcess()

            }

        }
    }


    private fun finisProcess() {
        val intent = Intent()
        intent.putExtra("isAuthenticated", isFaceAuthenticated)
        setResult(RESULT_OK,intent)
        finish()
    }



}
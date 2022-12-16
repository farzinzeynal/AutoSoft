package az.avtomatika.autosoft.ui.main.face_matching

import android.graphics.Bitmap
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.LayoutInflater
import android.view.View
import az.avtomatika.autosoft.R
import az.avtomatika.autosoft.base.BaseActivity
import az.avtomatika.autosoft.databinding.ActivityFaceMatchingBinding
import az.avtomatika.autosoft.util.Constants.FACE_MATCHING_REQUEST_CODE
import az.avtomatika.autosoft.util.Constants.USER_IMAGE
import az.avtomatika.autosoft.util.FACE_OPERATION_TYPE
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

    override val bindingInflater: (LayoutInflater) -> ActivityFaceMatchingBinding = ActivityFaceMatchingBinding::inflate

    private var userImageBitmap: Bitmap? = null
    private var captureBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        getCurrentImage()




    }

    private fun getCurrentImage() {
        userImageBitmap = decodeBase64(USER_IMAGE)
    }

    private fun initViews() {
        views.btnStartCapture.setOnClickListener(this)
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
            val firstImage = MatchFacesImage(userImageBitmap, ImageType.PRINTED, true)
            val secondImage = MatchFacesImage(captureBitmap,  ImageType.LIVE, true)
            val matchFacesRequest = MatchFacesRequest(arrayListOf(firstImage, secondImage))
            FaceSDK.Instance().matchFaces(matchFacesRequest) { matchFacesResponse: MatchFacesResponse ->
                val split = MatchFacesSimilarityThresholdSplit(matchFacesResponse.results, 0.75)
                if (split.matchedFaces.size > 0) {
                    loadingDown(FACE_OPERATION_TYPE.MATCH_SUCCESS)
                    val similarity = split.matchedFaces[0].similarity
                    val similarityPercent = String.format("%.2f", similarity * 100)
                    views.similatiryText.text = "Melumantlar doğrulandı\\n \\n Oxşarlıq dərəcəsi: $similarityPercent%"
                } else {
                    loadingDown(FACE_OPERATION_TYPE.MATCH_ERROR)
                    views.similatiryText.text = "Biomentrik məlumat tapılmadı"
                }
            }
        }

    }

    private fun loadingUp(){
        views.btnStartCapture.alpha = 0.5F
        views.btnStartCapture.isEnabled = false
        views.guideLayout.visibility = View.GONE
        views.loadingLayout.visibility = View.VISIBLE
    }

    private fun loadingDown(type: FACE_OPERATION_TYPE){

        views.btnStartCapture.alpha = 1.0F
        views.btnStartCapture.isEnabled = true
        views.btnStartCapture.text = "Bir daha cəhd et"
        views.loadingLayout.visibility = View.GONE
        views.guideLayout.visibility = View.GONE
        views.resultLayout.visibility = View.VISIBLE

        if (type==FACE_OPERATION_TYPE.MATCH_SUCCESS){
            views.resultAnimation.setAnimation(R.raw.icon_success_anim)
        }
        else{
            views.resultAnimation.setAnimation(R.raw.icon_failed_anim)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){

            R.id.btnStartCapture ->{
                startFaceCaptureActivity()
            }

        }
    }





}
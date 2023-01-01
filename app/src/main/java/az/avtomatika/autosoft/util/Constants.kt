package az.avtomatika.autosoft.util

import android.graphics.Bitmap
import az.avtomatika.autosoft.model.remote.response.ProfileData

object Constants {

    //request codes
    var FACE_MATCHING_REQUEST_CODE = 1201

    //api values
    var currentLang = "az"
    var appVersion = "1.0"
    var OS_Name = "Android"
    var OS_Version = "12"
    var sessToken = ""

    //profileDatas
    var profileDatas: ProfileData? = null
    var registeredUserImage: Bitmap? = null



}
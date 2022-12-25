package az.avtomatika.autosoft.util

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationRequest
import android.os.Build
import android.provider.Settings.Secure
import android.util.Base64
import az.avtomatika.autosoft.util.helper.LocationHelper
import com.google.android.gms.location.LocationServices

import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import java.io.ByteArrayOutputStream
import java.util.*


object UtilFunctions {

    @SuppressLint("HardwareIds")
    fun getDeviceUniqueID(context: Context): String {
        return Secure.getString(context.contentResolver, Secure.ANDROID_ID)
    }


    fun getRandomID(): String {
        return UUID.randomUUID().toString()
    }





    fun decodeBase64(inputBase64: String?): Bitmap? {
        val decodedByte = Base64.decode(inputBase64, 0)
        return BitmapFactory
            .decodeByteArray(decodedByte, 0, decodedByte.size)
    }



    fun encodeBitmapToBase64(bitmap: Bitmap, compressFormat: Bitmap.CompressFormat, quality: Int): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(compressFormat, quality, stream)
        val image = stream.toByteArray()
        return String(Base64.encode(image, Base64.DEFAULT))
    }






}


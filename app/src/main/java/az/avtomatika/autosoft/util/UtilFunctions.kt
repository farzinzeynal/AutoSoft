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


    @SuppressLint("MissingPermission")
    fun getUserCurrentLocation(
        activity: Activity,
        onLocationSet: (LocationHelper.LocationModel) -> Unit
    ) {
        var locationModel: LocationHelper.LocationModel? = null
        var list: MutableList<Address>? = null
        val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            mFusedLocationClient.getCurrentLocation(
                LocationRequest.QUALITY_HIGH_ACCURACY,
                object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                        CancellationTokenSource().token

                    override fun isCancellationRequested() = false
                })
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        val geocoder = Geocoder(activity, Locale.getDefault())
                        list = geocoder.getFromLocation(location.latitude, location.longitude, 1)

                        locationModel = LocationHelper.LocationModel(
                            list?.get(0)?.latitude.toString(),
                            list?.get(0)?.longitude.toString(),
                            list?.get(0)?.getAddressLine(0).toString()
                        )

                        locationModel?.let{ onLocationSet(it) }
                    }
                }
        }
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


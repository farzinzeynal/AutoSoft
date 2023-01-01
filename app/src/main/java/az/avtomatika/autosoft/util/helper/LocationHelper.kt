package az.avtomatika.autosoft.util.helper

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import androidx.core.app.ActivityCompat
import az.avtomatika.autosoft.base.BaseActivity
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import java.util.*

object LocationHelper {

    var LOCATION_REQUEST_CODE = 1292


    @SuppressLint("MissingPermission")
    fun getUserCurrentLocation(
        activity: Activity,
        onLocationSet: (LocationHelper.LocationModel) -> Unit
    ) {
        BaseActivity.loadingUp()
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
                    BaseActivity.loadingDown()
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

    fun checkPermissions(activity: Activity): Boolean {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }


    fun requestPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            LOCATION_REQUEST_CODE
        )
    }

    fun isLocationEnabled(context: Activity): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    class LocationModel(
        var latitude:String,
        var longitude:String,
        var address:String,
    )


}
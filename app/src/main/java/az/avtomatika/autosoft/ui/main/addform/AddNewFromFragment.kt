package az.avtomatika.autosoft.ui.main.addform

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import az.avtomatika.autosoft.base.BaseFragment
import az.avtomatika.autosoft.databinding.FragmentAddNewFormBinding
import az.avtomatika.autosoft.util.helper.LocationHelper
import az.avtomatika.autosoft.util.UtilFunctions
import org.json.JSONObject

class AddNewFromFragment :
    BaseFragment<FragmentAddNewFormBinding>(FragmentAddNewFormBinding::inflate) {


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCurrentLocation()

    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.S)
    fun getCurrentLocation() {
        if (LocationHelper.checkPermissions(requireActivity())) {
            if (LocationHelper.isLocationEnabled(requireActivity())) {
                LocationHelper.getUserCurrentLocation(requireActivity()) { location ->
                    views.textCurrentAdress.text =
                        "${location.address}, ${location.latitude} , ${location.longitude} "
                }

            } else {
                Toast.makeText(requireContext(), "Zəhmət olmasa GPS-i aktiv edin", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            LocationHelper.requestPermissions(requireActivity())
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
        if (requestCode == LocationHelper.LOCATION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getCurrentLocation()
            }
        }
    }


}
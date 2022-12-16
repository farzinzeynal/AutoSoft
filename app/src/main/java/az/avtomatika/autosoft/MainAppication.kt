package az.avtomatika.autosoft

import android.app.Application
import android.util.Log
import az.avtomatika.autosoft.util.MainShared
import az.avtomatika.autosoft.util.SharedPrefNames
import az.avtomatika.autosoft.util.SharedTypes
import az.avtomatika.autosoft.util.UtilFunctions
import az.avtomatika.autosoft.util.UtilFunctions.getDeviceUniqueID

class MainAppication : Application() {

    override fun onCreate() {
        super.onCreate()

        saveUUID()

    }

    private fun saveUUID() {
      val uuid = getDeviceUniqueID(this)
      MainShared(this,SharedTypes.DEVICE_IFNO).setSharedData(SharedPrefNames.DEVICE_UUID,uuid)
    }

}
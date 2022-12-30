package az.avtomatika.autosoft

import android.app.Application
import android.util.Log
import az.avtomatika.autosoft.di.apiModule
import az.avtomatika.autosoft.di.viewModelModule
import az.avtomatika.autosoft.util.MainShared
import az.avtomatika.autosoft.util.SharedPrefNames
import az.avtomatika.autosoft.util.SharedTypes
import az.avtomatika.autosoft.util.UtilFunctions
import az.avtomatika.autosoft.util.UtilFunctions.getDeviceUniqueID
import az.avtomatika.autosoft.util.UtilFunctions.getRandomID
import az.avtomatika.autosoft.util.UtilFunctions.getReqId
import az.avtomatika.autosoft.util.UtilFunctions.randomStringByKotlinRandom
import az.needforspeak.di.repositoryModule
import az.needforspeak.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainAppication : Application() {

    companion object {
        lateinit var instance: MainAppication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        saveUUID()
        initDependencyInjection()
        Log.i("RANDOM_ID",getRandomID())
    }



    private fun initDependencyInjection() {
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MainAppication)
            modules(
                apiModule,
                repositoryModule,
                retrofitModule,
                viewModelModule
            )
        }
        instance = this
    }


    private fun saveUUID() {
      val uuid = getDeviceUniqueID(this)
      MainShared(this,SharedTypes.DEVICE_IFNO).setSharedData(SharedPrefNames.DEVICE_UUID,uuid)
    }

}
package az.avtomatika.autosoft.util

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationRequest
import android.net.Uri
import android.os.Build
import android.provider.Settings.Secure
import android.util.Base64
import android.view.View
import androidx.annotation.RequiresApi
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import az.avtomatika.autosoft.R
import az.avtomatika.autosoft.util.helper.LocationHelper
import com.google.android.gms.location.LocationServices

import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import java.io.*
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt
import kotlin.streams.asSequence


object UtilFunctions {


    @SuppressLint("HardwareIds")
    fun getDeviceUniqueID(context: Context): String {
        return Secure.getString(context.contentResolver, Secure.ANDROID_ID)
    }


    fun getRandomID(): String {
        return UUID.randomUUID().toString()
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun getReqId(): String {
        val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789"
        return  java.util.Random().ints(20, 0, source.length)
            .asSequence()
            .map(source::get)
            .joinToString("")
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

    fun getNavOptions(): NavOptions? {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.nav_default_enter_anim)
            .setExitAnim(R.anim.nav_default_exit_anim)
            .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
            .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
            .build()
    }

    fun getNavOptionsDisableBack(view: View): NavOptions? {
        val navController = Navigation.findNavController(view)
        return NavOptions.Builder()
            .setPopUpTo(
                navController.getGraph().startDestination,
                false)
            .setEnterAnim(R.anim.nav_default_enter_anim)
            .setExitAnim(R.anim.nav_default_exit_anim)
            .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
            .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
            .build()
    }

    fun bitmapToFile(context: Context,bitmap:Bitmap): Uri {
        val wrapper = ContextWrapper(context)
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file,"${UUID.randomUUID()}.jpg")

        try{
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
            stream.flush()
            stream.close()
        }catch (e: IOException){
            e.printStackTrace()
        }

        return Uri.parse(file.absolutePath)
    }






}


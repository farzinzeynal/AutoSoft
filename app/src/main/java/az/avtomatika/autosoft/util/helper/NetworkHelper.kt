package az.avtomatika.autosoft.util.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import az.avtomatika.autosoft.util.core.MainPopupDialog

object NetworkHelper {

    fun isNetworkConnected(context: Context?): Boolean {
        val isConnected: Boolean
        try {
            val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            isConnected = activeNetwork?.isConnectedOrConnecting == true
        } catch (e: Exception) {
            return false
        }
        return isConnected
    }

    fun notifyNetwork(context: Context){
        MainPopupDialog.infoAlert(context, MainPopupDialog.InfoDatas("Diqqət","İnternet bağlantısı yoxdur"))
    }
}
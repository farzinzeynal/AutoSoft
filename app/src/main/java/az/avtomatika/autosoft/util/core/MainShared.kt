package az.avtomatika.autosoft.util

import android.content.Context

class MainShared (context: Context, SharedPref: SharedTypes) {


    val prefs = context.getSharedPreferences(SharedPref.name, Context.MODE_PRIVATE)



    fun <T> setSharedData(item: String, value: T) {
        val editor = prefs.edit()

        when (value) {
            is Int -> {
                editor.putInt(item, value)
            }
            is String -> {
                editor.putString(item, value)
            }
            is Boolean -> {
                editor.putBoolean(item, value)
            }
            else -> {
                editor.putString(item, value as String)
            }
        }
        editor.apply()
    }


    inline fun <reified T> getSharedData(item: String, default: T): T {
        return when (T::class) {
            Int::class -> {
                prefs.getInt(item, default as Int) as T
            }
            String::class -> {
                prefs.getString(item, default as String) as T
            }
            Boolean::class -> {
                prefs.getBoolean(item, default as Boolean) as T
            }
            else -> {
                prefs.getString(item, default as String) as T
            }
        }
    }

    fun isContains(prName:String) = prefs.contains(prName)


    fun clearShare(){
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }


    fun removeShare(prName:String){
        val editor = prefs.edit()
        editor.remove(prName)
        editor.apply()
    }
}


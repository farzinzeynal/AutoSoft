package az.avtomatika.autosoft.base

import android.R
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding
import az.avtomatika.autosoft.component.LoadingView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseActivity <B : ViewBinding> : AppCompatActivity(),
    CoroutineScope by CoroutineScope(
        Dispatchers.Main
    ) {

    protected lateinit var views: B
    abstract val bindingInflater: (LayoutInflater) -> B

    var loadingView: LoadingView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        ///setLocale(this, StaticValues.lang)
        views = bindingInflater.invoke(layoutInflater).apply {
            setContentView(root)
        }
        val rootLayout = findViewById<ViewGroup>(R.id.content)

        loading.observe(this) {
            if (it > 0) {
                rootLayout.removeView(loadingView)
                loadingView = LoadingView(this)
                rootLayout.addView(loadingView)
            } else {
                rootLayout.removeView(loadingView)
            }
        }
        onViewBindingCreated(savedInstanceState)

    }




    companion object LoadindObj {
        private val _loading: MutableLiveData<Int> = MutableLiveData(0)
        val loading: LiveData<Int> = _loading
        fun loadingUp() {
            _loading.postValue(_loading.value?.plus(1) ?: 0)
        }

        fun loadingDown() {
            _loading.postValue(_loading.value?.minus(1) ?: 0)
        }
    }


    open fun onViewBindingCreated(savedInstanceState: Bundle?) {}

    @CallSuper
    override fun onDestroy() {
        coroutineContext[Job]?.cancel()
        super.onDestroy()
    }

}
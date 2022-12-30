package az.avtomatika.autosoft.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.avtomatika.autosoft.base.BaseActivity
import az.avtomatika.autosoft.model.remote.request.CheckVersionRequest
import az.avtomatika.autosoft.model.remote.response.CheckVersionResponse
import az.avtomatika.autosoft.model.remote.response.LoginResponseModel
import az.avtomatika.autosoft.repository.LoginRepository
import az.avtomatika.autosoft.repository.SplashRepository
import az.avtomatika.autosoft.util.NetworkResult
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SplashVeiwModel (private val repository: SplashRepository): ViewModel()  {

    private val _checkVersionLiveData: MutableLiveData<NetworkResult<CheckVersionResponse>> = MutableLiveData()
    val checkVersionLiveData: LiveData<NetworkResult<CheckVersionResponse>> = _checkVersionLiveData

    fun checkVersion(checkVersionRequest: CheckVersionRequest) = viewModelScope.launch {
        repository.checkVersion(checkVersionRequest).collect { values ->
            _checkVersionLiveData.value = values
        }
    }

}
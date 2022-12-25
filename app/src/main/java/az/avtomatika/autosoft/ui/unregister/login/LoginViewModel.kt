package az.avtomatika.autosoft.ui.unregister.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.avtomatika.autosoft.base.BaseActivity
import az.avtomatika.autosoft.model.remote.response.LoginResponseModel
import az.avtomatika.autosoft.repository.LoginRepository
import az.avtomatika.autosoft.util.NetworkResult
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel (private val repository: LoginRepository): ViewModel()  {

    private val _loginResponse: MutableLiveData<NetworkResult<LoginResponseModel>> = MutableLiveData()
    val loginResponse: LiveData<NetworkResult<LoginResponseModel>> = _loginResponse

    fun loginUser(userName: String,password:String) = viewModelScope.launch {
        BaseActivity.loadingUp()
        repository.login(userName,password).collect { values ->
            BaseActivity.loadingDown()
            _loginResponse.value = values
        }
    }

}
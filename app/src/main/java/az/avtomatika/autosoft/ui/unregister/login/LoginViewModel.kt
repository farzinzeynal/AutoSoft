package az.avtomatika.autosoft.ui.unregister.login

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.avtomatika.autosoft.base.BaseActivity
import az.avtomatika.autosoft.model.remote.request.LoginRequest
import az.avtomatika.autosoft.model.remote.request.ProfileInfoRequest
import az.avtomatika.autosoft.model.remote.request.UpdateAvatarRequest
import az.avtomatika.autosoft.model.remote.response.LoginResponseModel
import az.avtomatika.autosoft.model.remote.response.ProfileData
import az.avtomatika.autosoft.model.remote.response.ProfileInfoResponse
import az.avtomatika.autosoft.model.remote.response.UpdateAvatarResponse
import az.avtomatika.autosoft.repository.LoginRepository
import az.avtomatika.autosoft.util.Constants
import az.avtomatika.autosoft.util.Constants.profileDatas
import az.avtomatika.autosoft.util.HttpMethods
import az.avtomatika.autosoft.util.NetworkResult
import az.avtomatika.autosoft.util.UtilFunctions.getRandomID
import az.avtomatika.autosoft.util.UtilFunctions.getReqId
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _loginResponse: MutableLiveData<NetworkResult<LoginResponseModel>> =
        MutableLiveData()
    val loginResponse: LiveData<NetworkResult<LoginResponseModel>> = _loginResponse

    private val _profileInfoLiveData: MutableLiveData<NetworkResult<ProfileInfoResponse>> =
        MutableLiveData()
    val profileInfoLiveData: LiveData<NetworkResult<ProfileInfoResponse>> = _profileInfoLiveData

    private val _updateAvatarLiveData: MutableLiveData<NetworkResult<UpdateAvatarResponse>> =
        MutableLiveData()
    val updateAvatarLiveData: LiveData<NetworkResult<UpdateAvatarResponse>> = _updateAvatarLiveData

    fun loginUser(username: String, password: String) = viewModelScope.launch {
        BaseActivity.loadingUp()
        val loginRequest = LoginRequest(
            appversion = Constants.appVersion,
            method = HttpMethods.login,
            fincode = username,
            lang = Constants.currentLang,
            password = password,
            os_name = Constants.OS_Name,
            os_version = Constants.OS_Version
        )
        repository.login(loginRequest).collect { values ->
            _loginResponse.value = values
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getProfileDatas(username: String) = viewModelScope.launch {
        val profileInfoRequest = ProfileInfoRequest(
            appversion = Constants.appVersion,
            method = HttpMethods.profileInfo,
            fincode = username,
            lang = Constants.currentLang,
            sesstoken = Constants.sessToken,
            reqid = getReqId(),
        )
        repository.getProfileInfo(profileInfoRequest).collect { values ->
            _profileInfoLiveData.value = values
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun updateProfileAvatar(imageUri: String) = viewModelScope.launch {
        BaseActivity.loadingUp()
        val updateAvatarRequest = UpdateAvatarRequest(
            appversion = Constants.appVersion,
            method = HttpMethods.updateProfileAvatar,
            fincode = profileDatas?.fincode,
            lang = Constants.currentLang,
            sesstoken = Constants.sessToken,
            reqid = getReqId(),
            avatarfile = imageUri
        )
        repository.updateAvatar(updateAvatarRequest).collect { values ->
            BaseActivity.loadingDown()
            _updateAvatarLiveData.value = values
        }
    }


    fun saveDatas(response: ProfileInfoResponse?) {
        val profileDatas = ProfileData(
            fincode = response?.data?.fincode,
            telephone = response?.data?.telephone,
            firstname = response?.data?.firstname,
            lastname = response?.data?.lastname,
            birthday = response?.data?.birthday,
            address = response?.data?.address,
            avatar = response?.data?.avatar
        )
        Constants.profileDatas = profileDatas
    }

}
package az.avtomatika.autosoft.ui.main.addform

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.avtomatika.autosoft.base.BaseActivity
import az.avtomatika.autosoft.model.remote.request.GetLastShiftRequest
import az.avtomatika.autosoft.model.remote.request.InsertNewShiftRequest
import az.avtomatika.autosoft.model.remote.response.GetLastShiftResponse
import az.avtomatika.autosoft.model.remote.response.InsertNewShiftResponse
import az.avtomatika.autosoft.repository.FormRepository
import az.avtomatika.autosoft.util.Constants
import az.avtomatika.autosoft.util.HttpMethods
import az.avtomatika.autosoft.util.NetworkResult
import az.avtomatika.autosoft.util.UtilFunctions
import kotlinx.coroutines.launch

class FormViewModel(private val repository: FormRepository): ViewModel() {

    private val _getLastShiftLiveData: MutableLiveData<NetworkResult<GetLastShiftResponse>> = MutableLiveData()
    val getLastShiftLiveData: LiveData<NetworkResult<GetLastShiftResponse>> = _getLastShiftLiveData

    private val _inserNewShiftLiveData: MutableLiveData<NetworkResult<InsertNewShiftResponse>> = MutableLiveData()
    val inserNewShiftLiveData: LiveData<NetworkResult<InsertNewShiftResponse>> = _inserNewShiftLiveData

    @RequiresApi(Build.VERSION_CODES.N)
    fun getLastShiftType() = viewModelScope.launch {
        BaseActivity.loadingUp()
        val getLastShiftRequest= GetLastShiftRequest(
            appversion = Constants.appVersion,
            method = HttpMethods.getLastShift,
            fincode = Constants.profileDatas?.fincode,
            lang = Constants.currentLang,
            sesstoken = Constants.sessToken,
            reqid = UtilFunctions.getReqId(),
        )
        repository.getLastShiftType(getLastShiftRequest).collect { values ->
             BaseActivity.loadingDown()
            _getLastShiftLiveData.value = values
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun insertNewShift(latitude: String, longitude: String, imageToSend: String, shiftType:String) = viewModelScope.launch {
        BaseActivity.loadingUp()
        val getLastShiftRequest= InsertNewShiftRequest(
            appversion = Constants.appVersion,
            method = HttpMethods.insertNewShift,
            fincode = Constants.profileDatas?.fincode,
            lang = Constants.currentLang,
            sesstoken = Constants.sessToken,
            reqid = UtilFunctions.getReqId(),
            type = shiftType,
            latitude = latitude,
            longitude = longitude,
            photo = imageToSend
        )
        repository.insertNewShift(getLastShiftRequest).collect { values ->
            BaseActivity.loadingDown()
            _inserNewShiftLiveData.value = values
        }
    }

}
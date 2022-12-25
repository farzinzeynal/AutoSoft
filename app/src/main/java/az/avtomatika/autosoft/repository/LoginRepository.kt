package az.avtomatika.autosoft.repository

import az.avtomatika.autosoft.base.BaseApiResponse
import az.avtomatika.autosoft.model.remote.request.LoginRequest
import az.avtomatika.autosoft.model.remote.response.LoginResponseModel
import az.avtomatika.autosoft.service.LoginService
import az.avtomatika.autosoft.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginRepository(private val service: LoginService) : BaseApiResponse() {

    suspend fun login(userName: String,password:String): Flow<NetworkResult<LoginResponseModel>> {
        return flow {
            emit(safeApiCall { service.loginUser(LoginRequest(userName,password)) })
        }.flowOn(Dispatchers.IO)
    }



}
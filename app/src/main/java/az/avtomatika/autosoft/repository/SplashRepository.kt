package az.avtomatika.autosoft.repository

import az.avtomatika.autosoft.base.BaseApiResponse
import az.avtomatika.autosoft.model.remote.request.CheckVersionRequest
import az.avtomatika.autosoft.model.remote.request.LoginRequest
import az.avtomatika.autosoft.model.remote.response.CheckVersionResponse
import az.avtomatika.autosoft.model.remote.response.LoginResponseModel
import az.avtomatika.autosoft.service.LoginService
import az.avtomatika.autosoft.service.SplashService
import az.avtomatika.autosoft.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SplashRepository(private val service: SplashService) : BaseApiResponse() {

    suspend fun checkVersion(checkVersionRequest: CheckVersionRequest): Flow<NetworkResult<CheckVersionResponse>> {
        return flow {
            emit(safeApiCall { service.checkLatestVersion(checkVersionRequest) })
        }.flowOn(Dispatchers.IO)
    }



}
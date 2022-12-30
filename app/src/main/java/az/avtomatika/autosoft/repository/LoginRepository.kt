package az.avtomatika.autosoft.repository

import az.avtomatika.autosoft.base.BaseApiResponse
import az.avtomatika.autosoft.model.remote.request.LoginRequest
import az.avtomatika.autosoft.model.remote.request.ProfileInfoRequest
import az.avtomatika.autosoft.model.remote.request.UpdateAvatarRequest
import az.avtomatika.autosoft.model.remote.response.LoginResponseModel
import az.avtomatika.autosoft.model.remote.response.ProfileInfoResponse
import az.avtomatika.autosoft.model.remote.response.UpdateAvatarResponse
import az.avtomatika.autosoft.service.LoginService
import az.avtomatika.autosoft.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginRepository(private val service: LoginService) : BaseApiResponse() {

    suspend fun login(loginRequest: LoginRequest): Flow<NetworkResult<LoginResponseModel>> {
        return flow {
            emit(safeApiCall { service.loginUser(loginRequest) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getProfileInfo(profileInfoRequest: ProfileInfoRequest): Flow<NetworkResult<ProfileInfoResponse>> {
        return flow {
            emit(safeApiCall { service.getProfileInfo(profileInfoRequest) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun updateAvatar(updateAvatarRequest: UpdateAvatarRequest): Flow<NetworkResult<UpdateAvatarResponse>> {
        return flow {
            emit(safeApiCall { service.updateAvatar(updateAvatarRequest) })
        }.flowOn(Dispatchers.IO)
    }



}
package az.avtomatika.autosoft.service

import az.avtomatika.autosoft.model.remote.request.LoginRequest
import az.avtomatika.autosoft.model.remote.request.ProfileInfoRequest
import az.avtomatika.autosoft.model.remote.request.UpdateAvatarRequest
import az.avtomatika.autosoft.model.remote.response.LoginResponseModel
import az.avtomatika.autosoft.model.remote.response.ProfileInfoResponse
import az.avtomatika.autosoft.model.remote.response.UpdateAvatarResponse
import az.avtomatika.autosoft.util.Endpoints
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("/")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponseModel>

    @POST("/")
    suspend fun getProfileInfo(@Body loginRequest: ProfileInfoRequest): Response<ProfileInfoResponse>

    @POST("/")
    suspend fun updateAvatar(@Body updateAvatarRequest: UpdateAvatarRequest): Response<UpdateAvatarResponse>

}
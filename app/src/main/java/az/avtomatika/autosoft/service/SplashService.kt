package az.avtomatika.autosoft.service

import az.avtomatika.autosoft.model.remote.request.CheckVersionRequest
import az.avtomatika.autosoft.model.remote.request.LoginRequest
import az.avtomatika.autosoft.model.remote.response.CheckVersionResponse
import az.avtomatika.autosoft.model.remote.response.LoginResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SplashService {

    @POST("/")
    suspend fun checkLatestVersion(@Body checkVersionRequest: CheckVersionRequest): Response<CheckVersionResponse>

}
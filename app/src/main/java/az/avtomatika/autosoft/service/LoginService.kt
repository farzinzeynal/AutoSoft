package az.avtomatika.autosoft.service

import az.avtomatika.autosoft.model.remote.request.LoginRequest
import az.avtomatika.autosoft.model.remote.response.LoginResponseModel
import az.avtomatika.autosoft.util.Endpoints
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST(Endpoints.LOGIN_ENDPOINT)
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponseModel>
}
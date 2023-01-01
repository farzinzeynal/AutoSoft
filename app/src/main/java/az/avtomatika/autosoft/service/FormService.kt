package az.avtomatika.autosoft.service

import az.avtomatika.autosoft.model.remote.request.GetLastShiftRequest
import az.avtomatika.autosoft.model.remote.request.InsertNewShiftRequest
import az.avtomatika.autosoft.model.remote.response.GetLastShiftResponse
import az.avtomatika.autosoft.model.remote.response.InsertNewShiftResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FormService {

    @POST("/")
    suspend fun getlastShiftType(@Body checkVersionRequest: GetLastShiftRequest): Response<GetLastShiftResponse>

    @POST("/")
    suspend fun inserNewShift(@Body insertShiftRequest: InsertNewShiftRequest): Response<InsertNewShiftResponse>

}
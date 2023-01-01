package az.avtomatika.autosoft.repository

import az.avtomatika.autosoft.base.BaseApiResponse
import az.avtomatika.autosoft.model.remote.request.GetLastShiftRequest
import az.avtomatika.autosoft.model.remote.request.InsertNewShiftRequest
import az.avtomatika.autosoft.model.remote.response.GetLastShiftResponse
import az.avtomatika.autosoft.model.remote.response.InsertNewShiftResponse
import az.avtomatika.autosoft.service.FormService
import az.avtomatika.autosoft.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FormRepository(private val service: FormService) : BaseApiResponse() {

    suspend fun getLastShiftType(getLastShiftRequest: GetLastShiftRequest): Flow<NetworkResult<GetLastShiftResponse>> {
        return flow {
            emit(safeApiCall { service.getlastShiftType(getLastShiftRequest) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun insertNewShift(insertNewShiftRequest: InsertNewShiftRequest): Flow<NetworkResult<InsertNewShiftResponse>> {
        return flow {
            emit(safeApiCall { service.inserNewShift(insertNewShiftRequest) })
        }.flowOn(Dispatchers.IO)
    }

}
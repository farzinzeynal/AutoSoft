package az.avtomatika.autosoft.model.remote.request

import az.avtomatika.autosoft.model.remote.response.BaseResponseModel

data class CheckVersionRequest(
    var appversion: String,
    var method: String,
)

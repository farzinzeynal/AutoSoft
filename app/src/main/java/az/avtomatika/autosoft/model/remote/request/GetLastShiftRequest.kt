package az.avtomatika.autosoft.model.remote.request

data class GetLastShiftRequest (
    var appversion: String?,
    var method: String?,
    var fincode: String?,
    var lang: String?,
    val sesstoken: String?,
    val reqid: String?
)
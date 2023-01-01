package az.avtomatika.autosoft.model.remote.request

data class InsertNewShiftRequest (
    var appversion: String?,
    var method: String?,
    var fincode: String?,
    var lang: String?,
    val sesstoken: String?,
    val reqid: String?,
    val type: String?,
    val latitude: String?,
    val longitude: String?,
    val photo: String?,
)
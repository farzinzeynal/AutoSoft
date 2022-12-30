package az.avtomatika.autosoft.model.remote.request

data class ProfileInfoRequest (
    val appversion: String?,
    val method: String?,
    val fincode: String?,
    val lang: String?,
    val sesstoken: String?,
    val reqid: String?,
)
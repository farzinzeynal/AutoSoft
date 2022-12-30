package az.avtomatika.autosoft.model.remote.request

data class LoginRequest(
    val appversion: String?,
    val method: String?,
    val fincode: String?,
    val lang: String?,
    val password: String?,
    val os_name: String?,
    val os_version: String?
)
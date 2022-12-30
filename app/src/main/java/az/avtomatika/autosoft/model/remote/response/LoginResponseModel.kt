package az.avtomatika.autosoft.model.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponseModel(
     val result: String,
     val data: LoginData?,
     val error: ErrorData?,
)

data class LoginData (
     val sesstoken: String?
)

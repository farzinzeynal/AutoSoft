package az.avtomatika.autosoft.model.remote.response

import android.provider.ContactsContract
import com.google.gson.annotations.SerializedName

data class UpdateAvatarResponse(
    val result: String,
    val data: Data,
    val error: ErrorData?,
)

data class Data(
    val type: String?,
    val url: String?,
)


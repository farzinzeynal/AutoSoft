package az.avtomatika.autosoft.model.remote.response

import android.provider.ContactsContract
import com.google.gson.annotations.SerializedName

data class ProfileInfoResponse(
    val result: String,
    val data: ProfileData,
    val error: ErrorData?,
)

data class ProfileData(
    val fincode: String?,
    val telephone: String?,
    val firstname: String?,
    val lastname: String?,
    val birthday: String?,
    val address: String?,
    val avatar: UserAvatar?,
)

class UserAvatar(
    val filetype: String,
    val url: String
)

package az.avtomatika.autosoft.model.remote.response

data class CheckVersionResponse (
    var result: String,
    var data: CheckVersionData,
    val error: ErrorData?,
)

data class CheckVersionData(
    var latest_version: String,
    var has_new: String,
    var force_update: String
)
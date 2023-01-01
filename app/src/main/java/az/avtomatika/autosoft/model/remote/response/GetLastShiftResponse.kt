package az.avtomatika.autosoft.model.remote.response

data class GetLastShiftResponse (
    var result: String?,
    var data: LastShifData?,
    val error: ErrorData?,
)

data class LastShifData (
    var date: String?,
    var type: String?
)

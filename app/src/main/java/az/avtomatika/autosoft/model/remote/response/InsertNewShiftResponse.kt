package az.avtomatika.autosoft.model.remote.response

data class InsertNewShiftResponse (
    var result: String?,
    var data: InsertShiftData?,
    val error: ErrorData?,
)

data class InsertShiftData (
    var shiftID: String?
)

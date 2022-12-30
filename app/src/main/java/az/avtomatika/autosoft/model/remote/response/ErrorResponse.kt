package az.avtomatika.autosoft.model.remote.response

data class ErrorResponse (
    val result: String,
    val error: ErrorData
)

data class ErrorData (
    val message: String,
    val debug: String
)

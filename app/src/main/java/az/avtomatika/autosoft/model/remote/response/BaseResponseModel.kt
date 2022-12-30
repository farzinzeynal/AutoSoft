package az.avtomatika.autosoft.model.remote.response

open class BaseResponseModel <T> (
    var result: String,
    var data: T,
)
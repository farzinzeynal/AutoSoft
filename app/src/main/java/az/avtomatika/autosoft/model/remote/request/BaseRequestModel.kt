package az.avtomatika.autosoft.model.remote.request

import java.io.Serializable

abstract class BaseRequestModel {
     var appversion: String=""
     var method: String=""
     var fincode: String=""
     var lang: String=""
}

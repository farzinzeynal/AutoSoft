package az.avtomatika.autosoft.ui

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import az.avtomatika.autosoft.R
import az.avtomatika.autosoft.component.TextInputView
import com.regula.facesdk.FaceSDK
import com.regula.facesdk.configuration.FaceCaptureConfiguration
import com.regula.facesdk.model.results.FaceCaptureResponse

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<TextInputView>(R.id.inputPassword).setInputTypePassWord()
        findViewById<TextInputView>(R.id.inputPassword).setHint("Password")
        findViewById<TextInputView>(R.id.inputUsername).setHint("Username")


        findViewById<LinearLayout>(R.id.startCapture).setOnClickListener {
            startFaceCaptureActivity()
        }


    }

    private fun startFaceCaptureActivity() {
        val configuration = FaceCaptureConfiguration.Builder().setCameraSwitchEnabled(true).build()

        FaceSDK.Instance().presentFaceCaptureActivity(this@MainActivity, configuration) { faceCaptureResponse: FaceCaptureResponse? ->
            if (faceCaptureResponse?.image != null) {

            }
        }
    }
}


package az.avtomatika.autosoft.util.core


import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.annotation.Keep
import androidx.appcompat.app.AlertDialog
import az.avtomatika.autosoft.R
import az.avtomatika.autosoft.util.PopupAnimTypes
import com.airbnb.lottie.LottieAnimationView

object MainPopupDialog {

    private var infoAlert: AlertDialog? = null

    interface AlertPopupAction {
        fun onPopupCancel(code: Int) {}
        fun onPopupConfirm(code: Int)
    }

    interface InfoPopUpDismissListener {
        fun onDismiss()
    }

    @Keep
    data class ConfirmDatas(
        var title: String,
        var message: String,
        var requestCode: Int,
        var okButtonTitle: String? = null,
        var cancelButtonTitle: String? = null
    )


    @Keep
    data class InfoDatas(
        var title: String,
        var message: String,
        var okButtonTitle: String? = null
    )

    fun infoAlert(context: Context, infoDatas: InfoDatas, eventHandler: InfoPopUpDismissListener? = null, animType:PopupAnimTypes) {

        try {
            if (infoAlert != null && infoAlert?.isShowing == true){
                infoAlert?.dismiss()
            }
        }catch (e : Exception){}

        val builder = AlertDialog.Builder(context).setTitle("").setCancelable(false)
        val view = LayoutInflater.from(context).inflate(R.layout.info_alert, null)
        builder.setView(view)
        val infoOkBtn: Button = view.findViewById(R.id.info_ok)
        val infoTitle: TextView = view.findViewById(R.id.info_title)
        val infoMessage: TextView = view.findViewById(R.id.info_message)
        val dialogAnimation: LottieAnimationView = view.findViewById(R.id.dialogAnimation)

        when(animType){
            PopupAnimTypes.SUCCES ->{
                dialogAnimation.setAnimation(R.raw.icon_success_anim)
            }
            PopupAnimTypes.ERROR ->{
                dialogAnimation.setAnimation(R.raw.icon_failed_anim)
            }
            PopupAnimTypes.WARNING ->{
                dialogAnimation.setAnimation(R.raw.warning_anim)
            }
        }

        infoTitle.text = infoDatas.title
        infoMessage.text = infoDatas.message
        infoAlert = builder.create()
        infoOkBtn.setOnClickListener {
            infoAlert?.dismiss()
            eventHandler?.onDismiss()
        }
        infoAlert?.show()
    }


}
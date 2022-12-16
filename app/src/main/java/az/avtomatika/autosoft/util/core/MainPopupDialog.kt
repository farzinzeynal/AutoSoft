package az.avtomatika.autosoft.util.core


import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.annotation.Keep
import androidx.appcompat.app.AlertDialog
import az.avtomatika.autosoft.R

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

    fun infoAlert(context: Context, infoDatas: InfoDatas, eventHandler: InfoPopUpDismissListener? = null) {

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
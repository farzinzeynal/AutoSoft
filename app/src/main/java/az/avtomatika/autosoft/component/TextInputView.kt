package az.avtomatika.autosoft.component

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import az.avtomatika.autosoft.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class TextInputView : RelativeLayout, View.OnFocusChangeListener{

    constructor(context: Context) : super(context)
    constructor(context: Context, attr : AttributeSet) : super(context,attr)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var textInput:TextInputEditText
    private var textInputLayout:TextInputLayout

    init {
        inflate(context, R.layout.input_text_ui, this)

        textInput = findViewById(R.id.number_input)
        textInputLayout = findViewById(R.id.textInputLayout)
    }




    @SuppressLint("UseCompatLoadingForDrawables")
    fun setInputTypePassWord(){
        textInput.transformationMethod = PasswordTransformationMethod.getInstance()
        textInputLayout.endIconDrawable = context.resources.getDrawable(R.drawable.pass_toggle_selector)
    }

    fun setHint(hintText:String){
        textInputLayout.hint = hintText
    }

    fun getText(): String {
        return textInputLayout.editText?.text.toString()
    }

    fun setText(text:String){
         textInputLayout.editText?.setText(text)
    }

    fun setError(errorText: String){
        textInputLayout.editText?.error = errorText
    }








    override fun onFocusChange(p0: View?, p1: Boolean) {



    }


}

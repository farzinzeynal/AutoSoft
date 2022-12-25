package az.avtomatika.autosoft.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import az.avtomatika.autosoft.R

class StartMatchingView : LinearLayout {
    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attr: AttributeSet? = null) : super(context, attr) {
        initView()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        val rootView = (context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.loading_layout, this, true)

    }
}
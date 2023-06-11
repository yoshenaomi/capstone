package com.dicoding.capstone.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.dicoding.capstone.R

class CustomEmailEditText : AppCompatEditText {
    private lateinit var iconEmail: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        iconEmail = ContextCompat.getDrawable(context, R.drawable.baseline_email_24) as Drawable
        setEditTextDrawable(iconEmail)
        compoundDrawablePadding = 10

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                when {
                    s.toString().isEmpty() -> {
                        error = context.getString(R.string.email_empty)
                    }

                    !Patterns.EMAIL_ADDRESS.matcher(s).matches() -> {
                        error = context.getString(R.string.invalid_email)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hint = context.getString(R.string.email)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun setEditTextDrawable(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText, topOfTheText, endOfTheText, bottomOfTheText
        )
    }
}

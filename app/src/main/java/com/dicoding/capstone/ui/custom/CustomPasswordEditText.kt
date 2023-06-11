package com.dicoding.capstone.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.dicoding.capstone.R

class CustomPasswordEditText : AppCompatEditText {
    private lateinit var iconPassword: Drawable

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

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hint = resources.getString(R.string.password)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        iconPassword =
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_lock_24) as Drawable
        setEditTextDrawable(iconPassword)
        compoundDrawablePadding = 10

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                when {
                    s.toString().isEmpty() -> {
                        error = context.getString(R.string.email_empty)
                    }

                    s.toString().length < 8 -> {
                        error = context.getString(R.string.invalid_password)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
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
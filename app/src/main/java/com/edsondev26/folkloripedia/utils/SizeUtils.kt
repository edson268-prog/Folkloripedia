package com.edsondev26.folkloripedia.utils

import android.util.TypedValue
import android.widget.TextView

private var fontSize = 20f

object SizeUtils {
    fun changeFontSize(change: Float, textView: TextView) {
        fontSize = (fontSize + change).coerceIn(16f, 32f)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
    }
}
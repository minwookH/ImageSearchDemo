package com.minwook.imagesearchdemo.util

import android.content.Context
import android.util.TypedValue

object ViewUtils {
    fun dpToPx(dp: Float, context: Context): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
}

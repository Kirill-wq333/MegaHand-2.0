package com.evothings.mhand.presentation.utils.maps

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat

fun getScaledMarkerDrawable(resources: Resources, iconRes: Int, scale: Float): Drawable? {
    val iconDrawable = ResourcesCompat.getDrawable(resources, iconRes, null) ?: return null
    val resizeWidth = iconDrawable.intrinsicWidth * scale
    val resizeHeight = iconDrawable.intrinsicHeight * scale
    val bitmap = Bitmap.createBitmap(resizeWidth.toInt(), resizeHeight.toInt(), Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    iconDrawable.setBounds(0, 0, canvas.width, canvas.height)
    iconDrawable.draw(canvas)

    return BitmapDrawable(resources, bitmap)
}
package com.example.editphotovideo.utils

import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.editphotovideo.library.colorpicker.ColorPickerPopUp
import kotlin.random.Random

fun Context.generateRandomColor(): String {
    val random = Random
    return String.format(
        "#%02x%02x%02x",
        random.nextInt(256),
        random.nextInt(256),
        random.nextInt(256)
    )
}

fun Context.showColorPicker(
    defaultColor: Int,
    onColorPicked: (String) -> Unit,
    onDismiss: (() -> Unit)? = null
) {
    val colorPickerPopUp = ColorPickerPopUp(this)
    colorPickerPopUp.setShowAlpha(true)
        .setDefaultColor(defaultColor)
        .setDialogTitle("Pick a Color")
        .setOnPickColorListener(object : ColorPickerPopUp.OnPickColorListener {
            override fun onColorPicked(color: Int) {
                val colorString = String.format("#%06X", (0xFFFFFF and color))
                Log.d("ColorPicker", colorString)
                onColorPicked(colorString)
                onDismiss?.invoke()
            }

            override fun onCancel() {
                colorPickerPopUp.dismissDialog()
                onDismiss?.invoke()
            }
        })
    colorPickerPopUp.show()
}

fun TextView.setDrawableTopWithTint(drawableRes: Int, colorRes: Int) {
    val drawable = ContextCompat.getDrawable(context, drawableRes)
    drawable?.let {
        val wrappedDrawable = it.mutate()
        wrappedDrawable.setTint( colorRes)
        setCompoundDrawablesWithIntrinsicBounds(null, wrappedDrawable, null, null)
    }
}

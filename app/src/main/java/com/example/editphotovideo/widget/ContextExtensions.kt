package com.example.editphotovideo.widget

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

/* ---------------------------------------------- Resources ---------------------------------------------- */
fun Any?.getTagDebug(): String {
    val className = this?.javaClass?.simpleName ?: "UnknownClass"
    return "$className - DOAN_1"
}

fun Context?.getResString(@StringRes stringId: Int): String {
    return this?.resources?.getString(stringId) ?: ""
}

fun Context?.getDrawableResource(@DrawableRes drawableId: Int): Drawable? {
    this?.let {
        return ContextCompat.getDrawable(it, drawableId)
    } ?: run {
        return null
    }
}

/* ---------------------------------------------- Toast ---------------------------------------------- */

fun Context?.showToast(message: String) {
    (this as? Activity)?.runOnUiThread {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun Context?.showToast(@StringRes stringId: Int) {
    val message = getResString(stringId)
    showToast(message)
}

//fun Context?.debugToast(message: String) {
//    if (BuildConfig.DEBUG) {
//        showToast(message)
//    }
//}

/* ---------------------------------------------- SnackBar ---------------------------------------------- */

fun Context?.showSnackBar(message: String) {
    (this as? Activity)?.runOnUiThread {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }
}

fun Context.spToPx(sp: Float): Float {
    return sp * resources.displayMetrics.scaledDensity
}

fun Context.pxToSp(px: Float): Float {
    return px / resources.displayMetrics.scaledDensity
}

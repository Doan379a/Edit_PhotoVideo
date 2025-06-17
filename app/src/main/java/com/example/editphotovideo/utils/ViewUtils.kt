package com.example.editphotovideo.utils

import android.app.TimePickerDialog
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.editphotovideo.R
import com.example.editphotovideo.widget.gone
import com.example.editphotovideo.widget.visible
import java.util.Calendar

object ViewUtils {
    fun setupSeekBar(seekBar: SeekBar, onChange: (Float) -> Unit) {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                onChange(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    fun setupSeekBarWithProgressLabel(
        seekBar: SeekBar,
        textView: TextView,
        minProgress: Int,
        maxProgress: Int,
        isCheckVisibility: Boolean? = false,
        onProgressChangedCallback: ((Int) -> Unit)? = null
    ) {
        seekBar.max = maxProgress
        seekBar.progress = minProgress

        textView.visibility = View.GONE
        textView.text = minProgress.toString()

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                textView.visible()
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (seekBar == null) return

                var newProgress = progress
                if (progress < minProgress) {
                    newProgress = minProgress
                    seekBar.progress = minProgress
                }

                textView.text = newProgress.toString()

                seekBar.post {
                    val thumb = seekBar.thumb ?: return@post
                    val thumbBounds = thumb.bounds

                    val thumbCenterX = thumbBounds.centerX()

                    val seekBarLocation = IntArray(2)
                    seekBar.getLocationOnScreen(seekBarLocation)

                    val parent = textView.parent as View
                    val parentLocation = IntArray(2)
                    parent.getLocationOnScreen(parentLocation)

                    val absoluteThumbX = seekBarLocation[0] + thumbCenterX
                    val relativeX = absoluteThumbX - parentLocation[0]

                    val textWidth = textView.width
                    val newX = relativeX - textWidth / 2f

                    textView.translationX = newX
                }
                onProgressChangedCallback?.invoke(newProgress)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (isCheckVisibility == true)textView.gone()
            }
        })
    }

    fun mapSeekBarToSpeechRate(progress: Int): Float {
        val minSpeed = 0.5f
        val maxSpeed = 2.0f
        return minSpeed + (progress / 100f) * (maxSpeed - minSpeed)
    }

    fun mapSpeechRateToSeekBar(speechRate: Float): Int {
        val minSpeed = 0.5f
        val maxSpeed = 2.0f
        return (((speechRate - minSpeed) / (maxSpeed - minSpeed)) * 100).toInt()
    }
    fun mapSeekBarToVolume(progress: Int): Float {
        return progress / 100f
    }

    fun formatTwoDigits(number: Number): String {
        return String.format("%.1f", number.toDouble())
    }

    fun showKeyboard(context: Context,view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view.requestFocus()
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    fun hideKeyboard(context: Context,view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
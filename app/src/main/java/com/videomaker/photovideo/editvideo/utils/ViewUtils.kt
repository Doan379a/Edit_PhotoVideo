package com.videomaker.photovideo.editvideo.utils

import android.app.TimePickerDialog
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import android.widget.TextView
import android.widget.VideoView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.videomaker.photovideo.editvideo.R
import com.videomaker.photovideo.editvideo.widget.gone
import com.videomaker.photovideo.editvideo.widget.visible
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

    fun createSeekBarChangeListener(
        videoView: VideoView,
        onStart: (() -> Unit)? = null,
        onStop: (() -> Unit)? = null
    ): SeekBar.OnSeekBarChangeListener {
        return object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser && videoView.duration > 0) {
                    val position = (videoView.duration * progress) / 100
                    videoView.seekTo(position)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                onStart?.invoke()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                onStop?.invoke()
            }
        }
    }

    fun showLoadingView(
        loadingView: View,
        show: Boolean
    ) {
        loadingView.visibility = if (show) View.VISIBLE else View.GONE
    }

     fun formatTime(millis: Int): String {
        val totalSeconds = millis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}
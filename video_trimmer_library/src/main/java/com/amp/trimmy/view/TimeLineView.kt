/*
 * MIT License
 *
 * Copyright (c) 2016 Knowledge, education for life.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.amp.trimmy.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.amp.trimmy.R
import com.amp.trimmy.utils.BackgroundExecutor
import com.amp.trimmy.utils.UiThreadExecutor

open class TimeLineView @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {
    private var videoUri: Uri? = null
    @Suppress("LeakingThis")
//    private var bitmapList: LongSparseArray<Bitmap>? = null
    private val bitmapList = ArrayList<Bitmap?>()

    override fun onSizeChanged(w: Int, h: Int, oldW: Int, oldH: Int) {
        super.onSizeChanged(w, h, oldW, oldH)
        /*if (w != oldW)
            getBitmap(w, h)*/
    }

    private fun getBitmap(viewWidth: Int, viewHeight: Int) {
        // Set thumbnail properties (Thumbs are squares)
        @Suppress("UnnecessaryVariable")
        if (videoUri == null)
            return

        val thumbSize = viewHeight
        val numThumbs = Math.ceil((viewWidth.toFloat() / thumbSize).toDouble()).toInt()
        bitmapList.clear()
        if (isInEditMode) {
            val bitmap = ThumbnailUtils.extractThumbnail(
                BitmapFactory.decodeResource(resources, android.R.drawable.sym_def_app_icon)!!, thumbSize, thumbSize
            )
            for (i in 0 until numThumbs)
                bitmapList.add(bitmap)
            return
        }
        BackgroundExecutor.cancelAll("", true)
        BackgroundExecutor.execute(object : BackgroundExecutor.Task("", 0L, "") {
            override fun execute() {
                try {
                    val thumbnailList = ArrayList<Bitmap?>()
                    val mediaMetadataRetriever = MediaMetadataRetriever()
                    mediaMetadataRetriever.setDataSource(context, videoUri)
                    // Retrieve media data
                    val videoLengthInMs =
                        mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)!!.toLong() * 1000L
                    val interval = videoLengthInMs / numThumbs
                    for (i in 0 until numThumbs) {
                        var bitmap: Bitmap? = if (VERSION.SDK_INT >= VERSION_CODES.O_MR1)
                            mediaMetadataRetriever.getScaledFrameAtTime(
                                i * interval, MediaMetadataRetriever.OPTION_CLOSEST_SYNC, thumbSize, thumbSize
                            )
                        else mediaMetadataRetriever.getFrameAtTime(
                            i * interval,
                            MediaMetadataRetriever.OPTION_CLOSEST_SYNC
                        )
                        if (bitmap != null)
                            bitmap = ThumbnailUtils.extractThumbnail(bitmap, thumbSize, thumbSize)
                        thumbnailList.add(bitmap)
                    }
                    mediaMetadataRetriever.release()
                    returnBitmaps(thumbnailList)
                } catch (e: Throwable) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e)
                }

            }
        }
        )
    }

    private fun returnBitmaps(thumbnailList: ArrayList<Bitmap?>) {
        UiThreadExecutor.runTask("", Runnable {
            bitmapList.clear()
            bitmapList.addAll(thumbnailList)
            invalidate()
        }, 0L)
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val cornerRadius = 20f
        val path = Path().apply {
            addRoundRect(
                0f, 0f, width.toFloat(), height.toFloat(),
                cornerRadius, cornerRadius,
                Path.Direction.CW
            )
        }
        canvas.clipPath(path)

        var x = 0
        val thumbSize = height
        for (bitmap in bitmapList) {
            bitmap?.let {
                canvas.drawBitmap(it, x.toFloat(), 0f, null)
                x += thumbSize
            }
        }

    }

    fun setVideo(data: Uri) {
        videoUri = data
        getBitmap(viewWidth = width, viewHeight = height)
    }
}

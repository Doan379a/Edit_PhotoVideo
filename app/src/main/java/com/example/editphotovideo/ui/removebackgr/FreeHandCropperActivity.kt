package com.example.editphotovideo.ui.removebackgr

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.net.Uri
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.core.graphics.drawable.toBitmap
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.databinding.ActivityFreeHandCropperBinding
import com.example.editphotovideo.library.zoomimg.Settings
import com.example.editphotovideo.utils.ImageUtils.saveBitmapToCache

import java.io.InputStream

class FreeHandCropperActivity : BaseActivity<ActivityFreeHandCropperBinding>() {
    private lateinit var mBitmap: Bitmap
    val resultingImage: Bitmap
        get() = binding.imageResult.drawable.toBitmap()

    override fun setViewBinding(): ActivityFreeHandCropperBinding {
        return ActivityFreeHandCropperBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val imageUri: Uri? = intent.getParcelableExtra("image_uri")
        imageUri?.let {
            val inputStream: InputStream? = contentResolver.openInputStream(it)
            mBitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            binding.someView.setBitmap(mBitmap)
            binding.someView.onCropReady = {
                binding.btnCrop.visibility = View.VISIBLE
            }
        }
    }

    override fun viewListener() {
        binding.btnCrop.setOnClickListener {
            cropImage()
        }

        binding.btnBack.setOnClickListener {
            val resultUri = saveBitmapToCache(this,resultingImage)
            val intent = Intent()
            intent.putExtra("cropped_image_uri", resultUri)
            setResult(RESULT_OK, intent)
            finish()
        }
        binding.btnRollback.setOnClickListener {
            binding.someView.resetView()
            binding.gestureFrameLayout.visibility = View.GONE
            binding.someView.visibility = View.VISIBLE
        }
    }

    override fun dataObservable() {

    }

    private fun cropImage() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val widthOfScreen = displayMetrics.widthPixels
        val heightOfScreen = displayMetrics.heightPixels

        val resultingImage = Bitmap.createBitmap(
            widthOfScreen,
            heightOfScreen,
            mBitmap.config ?: Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(resultingImage)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        val path = Path()
        val points = binding.someView.getPoints()
        if (points.isNotEmpty()) {
            path.moveTo(points[0].x.toFloat(), points[0].y.toFloat())
            for (i in 1 until points.size) {
                path.lineTo(points[i].x.toFloat(), points[i].y.toFloat())
            }
            path.close()
        }

        canvas.drawPath(path, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(mBitmap, 0f, 0f, paint)
        paint.xfermode = null
        paint.color = Color.WHITE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 20f
        canvas.drawPath(path, paint)

        binding.gestureFrameLayout.visibility = View.VISIBLE
        binding.someView.visibility = View.GONE
        binding.imageResult.controller.settings.apply {
            isZoomEnabled = true
            isPanEnabled = true
            isRotationEnabled = true
            isDoubleTapEnabled = true
            isPanEnabled = true
            maxZoom = 10f
            minZoom = 0.2f
            setFitMethod(Settings.Fit.INSIDE)
            setBoundsType(Settings.Bounds.INSIDE)
            setImage(1, 1)
        }

        binding.imageResult.setImageBitmap(resultingImage)
    }

}
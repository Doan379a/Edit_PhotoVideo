package com.example.editphotovideo.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.Gravity
import android.widget.ImageView
import androidx.core.content.FileProvider
import com.example.editphotovideo.library.zoomimg.Settings
import com.example.editphotovideo.library.zoomimg.views.GestureImageView
import java.io.File
import java.io.FileOutputStream

object ImageUtils {
    const val DEFAULT_FOLDER = "Photo_edit_video"
    fun setUpZoomSettings(zoomImageView: GestureImageView) {
        zoomImageView.controller.settings.apply {
            isZoomEnabled = true
            isPanEnabled = true
            isRotationEnabled = true
            isDoubleTapEnabled = true
            isPanEnabled = true
            maxZoom = 5f
            minZoom = 0.1f
            setFitMethod(Settings.Fit.NONE)
            setBoundsType(Settings.Bounds.OUTSIDE)
            setGravity(Gravity.CENTER)
            setImage(300, 300)
        }
    }

    fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri {
        val file = File(context.cacheDir, "cropped_${System.currentTimeMillis()}.png")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    fun getCorrectlyOrientedBitmap(context: Context, imageUri: Uri): Bitmap {
        val inputStream = context.contentResolver.openInputStream(imageUri)
        val exif = ExifInterface(inputStream!!)
        val orientation = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )
        inputStream.close()

        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
        val matrix = Matrix()

        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
            else -> return bitmap
        }

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream).also {
                inputStream?.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun resizeBitmapToView(bitmap: Bitmap, view: ImageView): Bitmap {
        val width = view.width
        val height = view.height
        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }
    fun getRealPathFromUri(context: Context, uri: Uri): String? {
        val projection = arrayOf(android.provider.MediaStore.Video.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        return cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(android.provider.MediaStore.Video.Media.DATA)
            it.moveToFirst()
            it.getString(columnIndex)
        }
    }
    fun getTempMovieDir(): File {
        val dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        val customDir = File(dcim, "$DEFAULT_FOLDER")
        if (!customDir.exists()) {
            customDir.mkdirs()
        }
        return customDir
    }

}
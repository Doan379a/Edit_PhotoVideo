package com.example.editphotovideo.ui.editor.crop

import android.app.Activity
import android.graphics.Bitmap
import android.widget.Toast
import com.example.editphotovideo.databinding.ActivityEditImageBinding
import com.example.editphotovideo.widget.gone
import com.example.editphotovideo.widget.tap
import com.example.editphotovideo.widget.visible

class CropImageIncludeController(
    private val activity: Activity,
    private var imageToCrop: Bitmap? = null,
    private val binding: ActivityEditImageBinding,
    private val onImageCropped: (Bitmap) -> Unit
) {


    fun updateImage(bitmap: Bitmap) {
        this.imageToCrop = bitmap
    }

    fun showCropImageView(isVisible: Boolean) {
        if (isVisible) {
            binding.includeCropImage.root.visible()
            setUpCropView()
        } else {
            binding.includeCropImage.root.gone()
        }
    }

    private fun setUpCropView() = binding.apply {
        includeCropImage.cropImageView.setImageBitmap(imageToCrop)

        includeCropImage.btnRatio11.tap {
            includeCropImage.cropImageView.setFixedAspectRatio(true)
            includeCropImage.cropImageView.setAspectRatio(1, 1)
        }

        includeCropImage.btnRatio43.tap {
            includeCropImage.cropImageView.setFixedAspectRatio(true)
            includeCropImage.cropImageView.setAspectRatio(4, 3)
        }

        includeCropImage.btnRatio169.tap {
            includeCropImage.cropImageView.setFixedAspectRatio(true)
            includeCropImage.cropImageView.setAspectRatio(16, 9)
        }

        includeCropImage.btnRatioFree.tap {
            includeCropImage.cropImageView.setFixedAspectRatio(false)
        }

        includeCropImage.btnSaveCrop.tap {
            val cropped = includeCropImage.cropImageView.getCroppedImage()
            if (cropped != null) {
                imageToCrop = cropped
                onImageCropped(cropped)
                includeCropImage.root.gone()
            } else {
                Toast.makeText(activity, "Không thể cắt ảnh", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

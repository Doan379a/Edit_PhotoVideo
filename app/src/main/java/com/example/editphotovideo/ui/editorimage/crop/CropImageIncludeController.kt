package com.example.editphotovideo.ui.editorimage.crop

import android.app.Activity
import android.graphics.Bitmap
import com.example.editphotovideo.R
import com.example.editphotovideo.databinding.ActivityEditImageBinding
import com.example.editphotovideo.ui.editorimage.sealed.CropResult
import com.example.editphotovideo.utils.setDrawableTopWithTint
import com.example.editphotovideo.widget.gone
import com.example.editphotovideo.widget.tap
import com.example.editphotovideo.widget.visible

class CropImageIncludeController(
    private val activity: Activity,
    private var imageToCrop: Bitmap? = null,
    private val binding: ActivityEditImageBinding,
    private val onImageCropped: (CropResult) -> Unit
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

        includeCropImage.tvRatio11.tap {
            setUpColorTab(1)
            includeCropImage.cropImageView.setFixedAspectRatio(true)
            includeCropImage.cropImageView.setAspectRatio(1, 1)
        }

        includeCropImage.tvRatio23.tap {
            setUpColorTab(2)
            includeCropImage.cropImageView.setFixedAspectRatio(true)
            includeCropImage.cropImageView.setAspectRatio(2, 3)
        }

        includeCropImage.tvRatio32.tap {
            setUpColorTab(3)
            includeCropImage.cropImageView.setFixedAspectRatio(true)
            includeCropImage.cropImageView.setAspectRatio(3, 2)
        }
        includeCropImage.tvRatio45.tap {
            setUpColorTab(4)
            includeCropImage.cropImageView.setFixedAspectRatio(true)
            includeCropImage.cropImageView.setAspectRatio(4, 5)
        }
        includeCropImage.tvRatio916.tap {
            setUpColorTab(5)
            includeCropImage.cropImageView.setFixedAspectRatio(true)
            includeCropImage.cropImageView.setAspectRatio(9, 16)
        }


        includeCropImage.imgSaveCrop.tap {
            val cropped = includeCropImage.cropImageView.getCroppedImage()
            if (cropped != null) {
                imageToCrop = cropped
                onImageCropped(CropResult.Success(cropped))
                includeCropImage.root.gone()
            } else {
                onImageCropped(CropResult.Error("Không thể cắt ảnh"))
            }
        }
        includeCropImage.imgClose.tap {
            includeCropImage.root.gone()
            onImageCropped(CropResult.Canceled)
        }
    }

    private fun setUpColorTab(selectedTab: Int) = binding.apply {
        val activeColor = activity.getColor(R.color.color_selector_tab)
        val inactiveColor = activity.getColor(R.color.color_selector_none_tab)
        includeCropImage.tvRatio11.setTextColor(inactiveColor)
        includeCropImage.tvRatio11.setDrawableTopWithTint(R.drawable.ic_1_1, inactiveColor)
        includeCropImage.tvRatio23.setTextColor(inactiveColor)
        includeCropImage.tvRatio23.setDrawableTopWithTint(R.drawable.ic_2_3, inactiveColor)
        includeCropImage.tvRatio32.setTextColor(inactiveColor)
        includeCropImage.tvRatio32.setDrawableTopWithTint(R.drawable.ic_3_2, inactiveColor)
        includeCropImage.tvRatio45.setTextColor(inactiveColor)
        includeCropImage.tvRatio45.setDrawableTopWithTint(R.drawable.ic_4_5, inactiveColor)
        includeCropImage.tvRatio916.setTextColor(inactiveColor)
        includeCropImage.tvRatio916.setDrawableTopWithTint(R.drawable.ic_9_16, inactiveColor)

        when (selectedTab) {
            1 -> {
                includeCropImage.tvRatio11.setTextColor(activeColor)
                includeCropImage.tvRatio11.setDrawableTopWithTint(R.drawable.ic_1_1, activeColor)
            }

            2 -> {
                includeCropImage.tvRatio23.setTextColor(activeColor)
                includeCropImage.tvRatio23.setDrawableTopWithTint(R.drawable.ic_2_3, activeColor)
            }

            3 -> {
                includeCropImage.tvRatio32.setTextColor(activeColor)
                includeCropImage.tvRatio32.setDrawableTopWithTint(R.drawable.ic_3_2, activeColor)
            }

            4 -> {
                includeCropImage.tvRatio45.setTextColor(activeColor)
                includeCropImage.tvRatio45.setDrawableTopWithTint(R.drawable.ic_4_5, activeColor)
            }

            5 -> {
                includeCropImage.tvRatio916.setTextColor(activeColor)
                includeCropImage.tvRatio916.setDrawableTopWithTint(R.drawable.ic_9_16, activeColor)
            }
        }
    }
}

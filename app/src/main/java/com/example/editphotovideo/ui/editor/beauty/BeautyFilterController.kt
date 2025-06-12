package com.example.editphotovideo.ui.editor.beauty

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.example.editphotovideo.R
import com.example.editphotovideo.databinding.ActivityEditImageBinding
import com.example.editphotovideo.utils.ImageUtils.resizeBitmapToView
import com.example.editphotovideo.utils.ViewUtils.setupSeekBar
import com.example.editphotovideo.widget.gone
import com.example.editphotovideo.widget.invisible
import com.example.editphotovideo.widget.tap
import com.example.editphotovideo.widget.visible
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageContrastFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilterGroup
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGaussianBlurFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSaturationFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSmoothToonFilter

class BeautyFilterController(
    private val context: Context,
    private val binding: ActivityEditImageBinding,
    private val gpuImage: GPUImage,
    private var imageApplyFilter: Bitmap? = null,
    private val saturationFilter: GPUImageSaturationFilter,
    private val smoothToonFilter: GPUImageSmoothToonFilter,
    private val brightnessFilter: GPUImageBrightnessFilter,
    private val contrastFilter: GPUImageContrastFilter,
    private val blurFilter: GPUImageGaussianBlurFilter,
    private val onCloseShow: () -> Unit
) {
    private var currentBlurValue = 0f
    fun updateImage(bitmap: Bitmap) {
        this.imageApplyFilter = bitmap
    }

    fun showBeauty(isVisible: Boolean) {
        if (isVisible) {
            binding.includeBeauty.root.visible()
            setupBeautyView()
        } else {
            binding.includeBeauty.root.gone()
        }
    }

    private fun setupBeautyView() = binding.apply {
        includeBeauty.tvDoneAdjust.tap {
            includeBeauty.root.gone()
            onCloseShow()
        }
        includeBeauty.imgClose.tap {
            includeBeauty.root.gone()
            onCloseShow()
        }

        includeBeauty.tvSkinColor.setOnClickListener {
            setUpColorTab(1)
        }

        includeBeauty.tvBlur.setOnClickListener {
            setUpColorTab(2)
        }

        includeBeauty.tvAcne.setOnClickListener {
            setUpColorTab(3)
        }

        setupSeekBar(includeBeauty.seekbarSkinColor) { value ->
            val normalized = (value - 50) / 100f
            val default=if(value==0f) 0f else normalized.coerceIn(-0.3f, 0.3f)
            brightnessFilter.setBrightness(default)
            includeBeauty.tvSeekbarSkinColor.text = "$value"
            applyAllFilters()
        }

        setupSeekBar(includeBeauty.seekbarBlur) { value ->
            currentBlurValue = value / 10f
            includeBeauty.tvSeekbarBlur.text = "$value"
            applyAllFilters()
        }

        setupSeekBar(includeBeauty.seekbarAcne) { value ->
            val factor = value / 100f
            smoothToonFilter.setBlurSize(if(value==0f) 0f else 1f + factor * 4f)
            brightnessFilter.setBrightness(if(value==0f) 0f else 0f + factor * 0.1f)
            contrastFilter.setContrast(if(value==0f) 0f else 1f - factor * 0.3f)
            includeBeauty.tvSeekbarAcne.text = "$value"
            applyAllFilters()
        }
    }

    private fun applyAllFilters() {
        val filters = mutableListOf<GPUImageFilter>()
        Log.d("BeautyFilterController", "Applying filters with currentBlurValue: ${filters.size}")
        if (currentBlurValue > 0f) {
            blurFilter.setBlurSize(currentBlurValue)
            filters.add(blurFilter)
        }

        // Add skin tone
        filters.add(saturationFilter)
        filters.add(brightnessFilter)
        filters.add(smoothToonFilter)
        filters.add(contrastFilter)
        Log.d("BeautyFilterController", "Applying filters with currentBlurValue: ${filters.size}")
        gpuImage.setFilter(GPUImageFilterGroup(filters))
        val filteredBitmap = gpuImage.getBitmapWithFilterApplied(imageApplyFilter)
        val resizedBitmap = resizeBitmapToView(filteredBitmap, binding.photoEditorView.source)
        binding.photoEditorView.source.setImageBitmap(resizedBitmap)
    }

    private fun setUpColorTab(selectedTab: Int) = binding.apply {
        val activeColor = context.getColor(R.color.blue_color_picker)
        val inactiveColor = context.getColor(R.color.black)

        // Reset tất cả về mặc định
        includeBeauty.tvSkinColor.setTextColor(inactiveColor)
        includeBeauty.tvBlur.setTextColor(inactiveColor)
        includeBeauty.tvAcne.setTextColor(inactiveColor)

        includeBeauty.linearSkinColor.invisible()
        includeBeauty.linearBlur.invisible()
        includeBeauty.linearAcne.invisible()

        // Hiển thị tab được chọn
        when (selectedTab) {
            1 -> {
                includeBeauty.tvSkinColor.setTextColor(activeColor)
                includeBeauty.linearSkinColor.visible()
            }
            2 -> {
                includeBeauty.tvBlur.setTextColor(activeColor)
                includeBeauty.linearBlur.visible()
            }
            3 -> {
                includeBeauty.tvAcne.setTextColor(activeColor)
                includeBeauty.linearAcne.visible()
            }
        }
    }

}

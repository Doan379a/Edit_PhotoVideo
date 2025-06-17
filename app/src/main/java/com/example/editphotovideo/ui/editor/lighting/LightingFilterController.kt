package com.example.editphotovideo.ui.editor.lighting

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.example.editphotovideo.R
import com.example.editphotovideo.databinding.ActivityEditImageBinding
import com.example.editphotovideo.ui.editor.sealed.ImageFilterResult
import com.example.editphotovideo.utils.ImageUtils.resizeBitmapToView
import com.example.editphotovideo.utils.ViewUtils.setupSeekBar
import com.example.editphotovideo.utils.setDrawableTopWithTint
import com.example.editphotovideo.widget.gone
import com.example.editphotovideo.widget.invisible
import com.example.editphotovideo.widget.tap
import com.example.editphotovideo.widget.visible
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilterGroup
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSaturationFilter

class LightingFilterController(
    private val context: Context,
    private val binding: ActivityEditImageBinding,
    private val gpuImage: GPUImage,
    private var imageApplyFilter: Bitmap? = null,
    private val brightnessFilter: GPUImageBrightnessFilter,
    private val saturationFilter: GPUImageSaturationFilter,
    private val onCloseShow: (ImageFilterResult) -> Unit
) {
    private var currentBrightness = 0f
    private var currentSaturation = 0f
    fun updateImage(bitmap: Bitmap) {
        this.imageApplyFilter = bitmap
    }

    fun showLighting(isVisible: Boolean) {
        if (isVisible) {
            binding.includeLighting.root.visible()
            setupBeautyView()
        } else {
            binding.includeLighting.root.gone()
        }
    }

    private fun setupBeautyView() = binding.apply {
        includeLighting.tvDoneLighting.tap {
            includeLighting.root.gone()
            onCloseShow(ImageFilterResult.Canceled)
        }
        includeBeauty.imgClose.tap {
            includeBeauty.root.gone()
            onCloseShow(ImageFilterResult.Canceled)
        }
        includeLighting.tvBritness.setOnClickListener {
            setUpColorTab(1)
        }

        includeLighting.tvSaturation.setOnClickListener {
            setUpColorTab(2)
        }

        setupSeekBar(includeLighting.seekbarBritness) { value ->
            currentBrightness = (value - 50) / 100f

            applyFiltersLighting()
        }

        setupSeekBar(includeLighting.seekbarSaturation) { value ->
            currentSaturation = (value - 50) / 100f
            applyFiltersLighting()
        }
    }


    private fun applyFiltersLighting() {
        val filters = mutableListOf<GPUImageFilter>()
        if (currentBrightness != 0f) {
            brightnessFilter.setBrightness(currentBrightness.coerceIn(-0.3f, 0.3f))
            filters.add(brightnessFilter)
        }

        if (currentSaturation != 0f) {
            val saturation = (1f + currentSaturation).coerceIn(0f, 2f)
            saturationFilter.setSaturation(saturation)
            filters.add(saturationFilter)
        }

        if (filters.isEmpty()) {
            val resizedBitmap = imageApplyFilter?.let {
                resizeBitmapToView(it, binding.photoEditorView.source)
            }
            binding.photoEditorView.source.setImageBitmap(resizedBitmap)
            onCloseShow(ImageFilterResult.Success(resizedBitmap))
            return
        }

        gpuImage.setFilter(GPUImageFilterGroup(filters))
        val filteredBitmap = gpuImage.getBitmapWithFilterApplied(imageApplyFilter)
        val resizedBitmap = resizeBitmapToView(filteredBitmap, binding.photoEditorView.source)
        binding.photoEditorView.source.setImageBitmap(resizedBitmap)
        onCloseShow(ImageFilterResult.Success(resizedBitmap))
    }

    private fun setUpColorTab(selectedTab: Int) = binding.apply {
        val activeColor = context.getColor(R.color.color_selector_tab)
        val inactiveColor = context.getColor(R.color.color_selector_none_tab)

        includeLighting.tvBritness.setTextColor(inactiveColor)
        includeLighting.tvBritness.setDrawableTopWithTint(R.drawable.ic_britness, inactiveColor)
        includeLighting.tvSaturation.setTextColor(inactiveColor)
        includeLighting.tvSaturation.setDrawableTopWithTint(R.drawable.ic_saturation, inactiveColor)
        includeLighting.linearBritness.invisible()
        includeLighting.linearSaturation.invisible()

        when (selectedTab) {
            1 -> {
                includeLighting.tvBritness.setTextColor(activeColor)
                includeLighting.tvBritness.setDrawableTopWithTint(
                    R.drawable.ic_britness,
                    activeColor
                )
                includeLighting.linearBritness.visible()
            }

            2 -> {
                includeLighting.tvSaturation.setTextColor(activeColor)
                includeLighting.tvSaturation.setDrawableTopWithTint(
                    R.drawable.ic_saturation,
                    activeColor
                )
                includeLighting.linearSaturation.visible()
            }
        }
    }

}
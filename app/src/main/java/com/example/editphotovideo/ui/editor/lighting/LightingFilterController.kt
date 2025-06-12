package com.example.editphotovideo.ui.editor.lighting

import android.content.Context
import android.graphics.Bitmap
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
    private val onCloseShow: () -> Unit
) {
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
            onCloseShow()
        }
        includeBeauty.imgClose.tap {
            includeBeauty.root.gone()
            onCloseShow()
        }
        includeLighting.tvBritness.setOnClickListener {
            setUpColorTab(1)
        }

        includeLighting.tvSaturation.setOnClickListener {
            setUpColorTab(2)
        }

        setupSeekBar(includeLighting.seekbarBritness) { value ->
            val normalized = (value - 50) / 100f
            brightnessFilter.setBrightness(normalized.coerceIn(-0.3f, 0.3f))
            includeLighting.tvSeekbarBritness.text = "$value"
            applyFiltersLighting()
        }

        setupSeekBar(includeLighting.seekbarSaturation) { value ->
            val normalized = (value - 50) / 100f
            saturationFilter.setSaturation(normalized.coerceIn(-1f, 1f))
            includeLighting.tvSeekbarSaturation.text = "$value"
            applyFiltersLighting()
        }
    }


    private fun applyFiltersLighting() {
        val filters = mutableListOf<GPUImageFilter>()
        filters.add(saturationFilter)
        filters.add(brightnessFilter)
        gpuImage.setFilter(GPUImageFilterGroup(filters))
        val filteredBitmap = gpuImage.getBitmapWithFilterApplied(imageApplyFilter)
        val resizedBitmap = resizeBitmapToView(filteredBitmap, binding.photoEditorView.source)
        binding.photoEditorView.source.setImageBitmap(resizedBitmap)
//        Glide.with(context).load(filteredBitmap)
//            .into(binding.photoEditorView.source)
    }

    private fun setUpColorTab(selectedTab: Int) = binding.apply {
        val activeColor = context.getColor(R.color.blue_color_picker)
        val inactiveColor = context.getColor(R.color.black)

        includeLighting.tvBritness.setTextColor(inactiveColor)
        includeLighting.tvSaturation.setTextColor(inactiveColor)
        includeLighting.linearBritness.invisible()
        includeLighting.linearSaturation.invisible()

        when (selectedTab) {
            1 -> {
                includeLighting.tvBritness.setTextColor(activeColor)
                includeLighting.linearBritness.visible()
            }
            2 -> {
                includeLighting.tvSaturation.setTextColor(activeColor)
                includeLighting.linearSaturation.visible()
            }
        }
    }

}
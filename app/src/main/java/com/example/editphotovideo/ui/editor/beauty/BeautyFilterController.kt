package com.example.editphotovideo.ui.editor.beauty

import android.content.Context
import android.graphics.Bitmap
import android.provider.ContactsContract.CommonDataKinds.Im
import android.util.Log
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
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
import jp.co.cyberagent.android.gpuimage.filter.GPUImageContrastFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilterGroup
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGaussianBlurFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSaturationFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSmoothToonFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    private val onCloseShow: (ImageFilterResult) -> Unit
) {
    private var filterJob: Job? = null
    private var currentBlurValue = 0f
    private var currentAcne=0f
    private var currentSkinColor = 0f
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
            onCloseShow(ImageFilterResult.Canceled)
        }
        includeBeauty.imgClose.tap {
            includeBeauty.root.gone()
            onCloseShow(ImageFilterResult.Canceled)
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
            currentSkinColor = (value - 50) / 100f

            includeBeauty.tvSeekbarSkinColor.text = "$value"
            applyAllFiltersDebounced()
        }

        setupSeekBar(includeBeauty.seekbarBlur) { value ->
            currentBlurValue = value / 10f
            includeBeauty.tvSeekbarBlur.text = "$value"
            applyAllFiltersDebounced()
        }

        setupSeekBar(includeBeauty.seekbarAcne) { value ->
            currentAcne = value / 100f

            includeBeauty.tvSeekbarAcne.text = "$value"
            applyAllFiltersDebounced()
        }
    }


    private fun applyAllFiltersDebounced() {
        filterJob?.cancel()
        filterJob = CoroutineScope(Dispatchers.Main).launch {
            delay(50)
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
        if (currentAcne > 0f) {
            smoothToonFilter.setBlurSize(1f + currentAcne * 4f)
            brightnessFilter.setBrightness(0f + currentAcne * 0.1f)
            contrastFilter.setContrast(1f - currentAcne * 0.3f)
            filters.add(smoothToonFilter)
            filters.add(contrastFilter)
            filters.add(brightnessFilter)
        }

        if (currentSkinColor > 0f) {
            Log.d("BeautyFilterController", "currentSkinColor: $currentSkinColor")

            saturationFilter.setSaturation(1f + currentSkinColor * 2f)
            filters.add(saturationFilter)
        }else {
            Log.d("BeautyFilterController", "currentSkinColor: $currentSkinColor")
                saturationFilter.setSaturation(1f)
        }
        if (filters.isEmpty()) {
            val resizedBitmap = imageApplyFilter?.let { resizeBitmapToView(it, binding.photoEditorView.source) }
            binding.photoEditorView.source.setImageBitmap(resizedBitmap)
            onCloseShow(ImageFilterResult.Success(resizedBitmap))
            return
        }
        Log.d("BeautyFilterController", "Applying filters with currentBlurValue: ${filters.size}")
        gpuImage.setFilter(GPUImageFilterGroup(filters))
        val filteredBitmap = gpuImage.getBitmapWithFilterApplied(imageApplyFilter)
        val resizedBitmap = resizeBitmapToView(filteredBitmap, binding.photoEditorView.source)
        binding.photoEditorView.source.setImageBitmap(resizedBitmap)
        onCloseShow(ImageFilterResult.Success(resizedBitmap))
    }

    private fun setUpColorTab(selectedTab: Int) = binding.apply {
        val activeColor = context.getColor(R.color.color_selector_tab)
        val inactiveColor = context.getColor(R.color.color_selector_none_tab)
        includeBeauty.tvSkinColor.setTextColor(inactiveColor)
        includeBeauty.tvSkinColor.setDrawableTopWithTint(R.drawable.ic_skin_color,inactiveColor)
        includeBeauty.tvBlur.setTextColor(inactiveColor)
        includeBeauty.tvBlur.setDrawableTopWithTint(R.drawable.ic_blur,inactiveColor)
        includeBeauty.tvAcne.setTextColor(inactiveColor)
        includeBeauty.tvAcne.setDrawableTopWithTint(R.drawable.ic_acne,inactiveColor)

        includeBeauty.linearSkinColor.invisible()
        includeBeauty.linearBlur.invisible()
        includeBeauty.linearAcne.invisible()

        // Hiển thị tab được chọn
        when (selectedTab) {
            1 -> {
                includeBeauty.tvSkinColor.setTextColor(activeColor)
                includeBeauty.tvSkinColor.setDrawableTopWithTint(R.drawable.ic_skin_color,activeColor)
                includeBeauty.linearSkinColor.visible()
            }
            2 -> {
                includeBeauty.tvBlur.setTextColor(activeColor)
                includeBeauty.tvBlur.setDrawableTopWithTint(R.drawable.ic_blur,activeColor)
                includeBeauty.linearBlur.visible()
            }
            3 -> {
                includeBeauty.tvAcne.setTextColor(activeColor)
                includeBeauty.tvAcne.setDrawableTopWithTint(R.drawable.ic_acne,activeColor)
                includeBeauty.linearAcne.visible()
            }
        }
    }


}

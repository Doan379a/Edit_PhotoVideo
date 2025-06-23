package com.example.editphotovideo.ui.tools.compressvideo

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.editphotovideo.R
import com.example.editphotovideo.base.BaseBottomSheetFragment
import com.example.editphotovideo.databinding.BottomSheetCompressBinding

class CompressBottomSheet(
    activity: Activity,
    val durationSec: Long,
    val originWidth: Int,
    val originHeight: Int,
    val originBitrate: Int,
    val onOptionSelected: (width: Int, height: Int, bitrate: Int) -> Unit
) : BaseBottomSheetFragment<BottomSheetCompressBinding>() {


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): BottomSheetCompressBinding {
        return BottomSheetCompressBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val originalSizeMB = originBitrate.toDouble() * durationSec / 8 / 1024 / 1024
        binding.tvQuality.text =
            requireActivity().getString(R.string.quality) + " " + "${originWidth}×${originHeight}"
        binding.tvApproximateSize.text =
            requireActivity().getString(R.string.approximate_size) + " " + "${
                "%.1f".format(originalSizeMB)
            } MB"
        val options = listOf(
            Triple(864, 1920, 5_000_000),
            Triple(648, 1440, 3_000_000),
            Triple(432, 960, 1_000_000)
        )
        val radioButtons = listOf(binding.rbOption1, binding.rbOption2, binding.rbOption3)
        val layouts = listOf(binding.llLow, binding.llMedium, binding.llHigh)


        layouts.forEachIndexed { index, layout ->
            layout.setOnClickListener {
                binding.rgOptions.check(radioButtons[index].id)
            }
        }
        binding.rgOptions.check(binding.rbOption1.id)

        binding.tvCancel.setOnClickListener { dismiss() }
        binding.tvStart.setOnClickListener {
            val checkedId = binding.rgOptions.checkedRadioButtonId
            val selectedIndex = radioButtons.indexOfFirst { it.id == checkedId }
            val (w, h, bitrate) = options[selectedIndex]
            dismiss()
            onOptionSelected(w, h, bitrate)
        }
    }

    override fun bindView() {

    }

}
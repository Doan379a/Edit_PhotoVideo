package com.videomaker.photovideo.editvideo.dialog

import android.app.Activity
import android.view.LayoutInflater
import com.videomaker.photovideo.editvideo.R
import com.videomaker.photovideo.editvideo.base.BaseDialog
import com.videomaker.photovideo.editvideo.databinding.DialogAlertBinding
import com.videomaker.photovideo.editvideo.widget.tap

class AlertDialog(
    activity1: Activity,
    val title: String? = null,
    val iconBack: Int? = null,
    private var actionExit: () -> Unit,
) : BaseDialog<DialogAlertBinding>(activity1, true) {


    override fun getContentView(): DialogAlertBinding {
        return DialogAlertBinding.inflate(LayoutInflater.from(activity))
    }

    override fun initView() {
        binding.tvTitle.text = title ?:binding.tvTitle.text
        binding.imgClose.setImageResource(iconBack ?: R.drawable.ic_back)
    }

    override fun bindView() {
        binding.apply {
            imgClose.tap {
                dismiss()
            }
            tvExit.tap {
                actionExit.invoke()
            }
        }
    }
}
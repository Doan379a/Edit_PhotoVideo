package com.example.editphotovideo.dialog

import android.app.Activity
import android.view.LayoutInflater
import com.example.editphotovideo.R
import com.example.editphotovideo.base.BaseDialog
import com.example.editphotovideo.databinding.DialogAlertBinding
import com.example.editphotovideo.widget.tap

class BackAlertDialog(
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
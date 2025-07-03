package com.videomaker.photovideo.editvideo.dialog

import android.app.Activity
import android.view.LayoutInflater
import com.videomaker.photovideo.editvideo.databinding.DialogPermissionBinding
import com.videomaker.photovideo.editvideo.base.BaseDialog
import com.videomaker.photovideo.editvideo.widget.tap


class PermissionDialog(
    activity1: Activity,
    private var action: () -> Unit
) : BaseDialog<DialogPermissionBinding>(activity1, true) {
    override fun getContentView(): DialogPermissionBinding {
        return DialogPermissionBinding.inflate(LayoutInflater.from(activity))
    }

    override fun initView() {

    }

    override fun bindView() {
        binding.apply {
            txtGo.tap {
                action.invoke()
                dismiss()
            }
        }
    }


}
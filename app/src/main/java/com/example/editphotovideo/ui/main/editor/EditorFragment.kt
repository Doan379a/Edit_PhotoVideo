package com.example.editphotovideo.ui.main.editor

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.editphotovideo.base.BaseFragment
import com.example.editphotovideo.databinding.FragmentEditorBinding
import com.example.editphotovideo.ui.editor.EditImageActivity
import com.example.editphotovideo.ui.removebackgr.RemoveBackGrActivity
import gun0912.tedimagepicker.builder.TedImagePicker

class EditorFragment : BaseFragment<FragmentEditorBinding>() {
    override fun setViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditorBinding {
        return FragmentEditorBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.imgRemoveBackgr.setOnClickListener {
            removeBackGround()
        }
        binding.imgEnhanceBeauty.setOnClickListener {
            selectImageEdit()
        }
    }

    override fun viewListener() {
    }

    override fun dataObservable() {
    }

    private fun selectImageEdit() {
        TedImagePicker.with(requireActivity())
            .cancelListener {
                Log.d("TedImagePicker", "Người dùng đã hủy chọn ảnh")
            }
            .errorListener {
                Log.d("TedImagePicker", "Lỗi khi chọn ảnh!")
            }
            .start { uri ->
                val intent = Intent(requireActivity(), EditImageActivity::class.java).apply {
                    putExtra(
                        "URI_IMAGE_INPUT",
                        uri
                    )
                }

                startActivity(intent)
            }
    }

    private fun removeBackGround() {
        TedImagePicker.with(requireActivity())
            .cancelListener {
                Log.d("TedImagePicker", "Người dùng đã hủy chọn ảnh")
            }
            .errorListener {
                Log.d("TedImagePicker", "Lỗi khi chọn ảnh!")
            }
            .start { uri ->
                val intent = Intent(requireActivity(), RemoveBackGrActivity::class.java).apply {
                    putExtra(
                        "URI_IMAGE_INPUT",
                        uri
                    )
                }
                startActivity(intent)
            }
    }
}
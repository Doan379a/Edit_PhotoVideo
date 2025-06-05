package com.example.editphotovideo.ui.main

import android.content.Intent
import android.util.Log
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.databinding.ActivityMainBinding
import com.example.editphotovideo.ui.removebackgr.RemoveBackGrActivity
import gun0912.tedimagepicker.builder.TedImagePicker


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun setViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {

    }

    override fun viewListener() {
        binding.btnRemoveBackgr.setOnClickListener {
            removeBackGround()
        }
    }

    override fun dataObservable() {
    }

    private fun removeBackGround() {
        TedImagePicker.with(this)
            .cancelListener {
                Log.d("TedImagePicker", "Người dùng đã hủy chọn ảnh")
            }
            .errorListener {
                Log.d("TedImagePicker", "Lỗi khi chọn ảnh!")
            }
            .start { uri ->
                val intent = Intent(this, RemoveBackGrActivity::class.java).apply {
                    putExtra(
                        "URI_IMAGE_INPUT",
                        uri
                    )
                }

                startActivity(intent)
            }
    }
}
package com.example.editphotovideo.ui.main

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.databinding.ActivityMainBinding
import com.example.editphotovideo.ui.editor.EditImageActivity
import com.example.editphotovideo.ui.removebackgr.RemoveBackGrActivity
import gun0912.tedimagepicker.builder.TedImagePicker


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun setViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri = result.data?.getParcelableExtra<Uri>("output_image_uri")
                binding.imageBackground.setImageURI(uri)
            }
        }

    override fun initView() {

    }

    override fun viewListener() {
        binding.btnRemoveBackgr.setOnClickListener {
            removeBackGround()
        }
        binding.btnEditImage.setOnClickListener {
            selectImageEdit()
        }
    }

    override fun dataObservable() {
    }

    private fun selectImageEdit() {
        TedImagePicker.with(this)
            .cancelListener {
                Log.d("TedImagePicker", "Người dùng đã hủy chọn ảnh")
            }
            .errorListener {
                Log.d("TedImagePicker", "Lỗi khi chọn ảnh!")
            }
            .start { uri ->
                val intent = Intent(this, EditImageActivity::class.java).apply {
                    putExtra(
                        "URI_IMAGE_INPUT",
                        uri
                    )
                }

                startActivity(intent)
            }
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

                launcher.launch(intent)
            }
    }
}
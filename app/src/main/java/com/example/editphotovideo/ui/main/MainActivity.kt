package com.example.editphotovideo.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.data.ImageData
import com.example.editphotovideo.databinding.ActivityMainBinding
import com.example.editphotovideo.service.CreateVideoService
import com.example.editphotovideo.service.ImageCreatorService
import com.example.editphotovideo.ui.editmovie.ImageEditActivity
import com.example.editphotovideo.ui.editmovie.MyApplication
import com.example.editphotovideo.ui.removebackgr.RemoveBackGrActivity
import com.example.editphotovideo.widget.tap
import gun0912.tedimagepicker.builder.TedImagePicker
import java.net.URI


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var application: MyApplication

    override fun setViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        application = MyApplication.getInstance()


    }

    override fun viewListener() {
        binding.btnRemoveBackgr.setOnClickListener {
            removeBackGround()
        }
        binding.btnEditVideo.tap {
            TedImagePicker.with(this)
                .cancelListener {
                    Log.d("TedImagePicker", "Người dùng đã hủy chọn ảnh")
                }
                .errorListener {
                    Log.d("TedImagePicker", "Lỗi khi chọn ảnh!")
                }

                .startMultiImage { uriList ->
                    application.clearAllSelection()
                    uriList.forEach { uri ->
                        val imagePath = getRealPathFromUri(this, uri)
                        val imageData = ImageData().apply {
                            this.imagePath = imagePath
                            this.folderName = "FromPicker"
                            this.imageCount = 1
                        }
                        application.addSelectedImage(imageData)
                    }
                    if (!isVideoInprocess()) {
                        val intent = Intent(this, ImageEditActivity::class.java)
                        intent.putParcelableArrayListExtra("selectedImages", ArrayList(uriList))
                        startActivity(intent)
                    }
                }
        }
    }
    private fun isVideoInprocess(): Boolean {
        return MyApplication.isMyServiceRunning(
            this,
            CreateVideoService::class.java
        ) || MyApplication.isMyServiceRunning(
            this,
            ImageCreatorService::class.java
        )
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
    fun getRealPathFromUri(context: Context, uri: Uri): String {
        var path = ""
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            path = it.getString(columnIndex)
        }
        return path
    }

}
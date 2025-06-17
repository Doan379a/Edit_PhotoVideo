package com.example.editphotovideo.ui.main

import android.content.Context
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.editphotovideo.R
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.data.ImageData
import com.example.editphotovideo.databinding.ActivityMainBinding
import com.example.editphotovideo.ui.editor.EditImageActivity
import com.example.editphotovideo.service.CreateVideoService
import com.example.editphotovideo.service.ImageCreatorService
import com.example.editphotovideo.ui.editmovie.ImageEditActivity
import com.example.editphotovideo.ui.editmovie.MyApplication
import com.example.editphotovideo.ui.removebackgr.RemoveBackGrActivity
import com.example.editphotovideo.utils.setDrawableTopWithTint
import com.example.editphotovideo.widget.invisible
import com.example.editphotovideo.widget.tap
import com.example.editphotovideo.widget.visible
import com.example.editphotovideo.widget.tap
import gun0912.tedimagepicker.builder.TedImagePicker
import java.net.URI


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var viewPagerAdapter: MainAdapter
    private lateinit var application: MyApplication

    override fun setViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }


    override fun initView() {
        application = MyApplication.getInstance()
        viewPagerAdapter = MainAdapter(this)
        binding.viewPager2.adapter = viewPagerAdapter
        binding.viewPager2.isUserInputEnabled = false
    }

    override fun viewListener() {
        binding.tvEditor.setOnClickListener {
            setUpColorTab(1)
        }
        binding.tvTemplate.setOnClickListener {
            setUpColorTab(2)
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

    override fun dataObservable() {
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
    private fun setUpColorTab(selectedTab: Int) = binding.apply {
        val activeColor = getColor(R.color.color_selector_tab)
        val inactiveColor = getColor(R.color.color_selector_none_tab)
        binding.tvEditor.setTextColor(inactiveColor)
        binding.tvEditor.setDrawableTopWithTint(R.drawable.ic_editor, inactiveColor)
        binding.tvTemplate.setTextColor(inactiveColor)
        binding.tvTemplate.setDrawableTopWithTint(R.drawable.ic_template, inactiveColor)

        when (selectedTab) {
            1 -> {
                binding.tvEditor.setTextColor(activeColor)
                binding.tvEditor.setDrawableTopWithTint(R.drawable.ic_editor, activeColor)
                binding.viewPager2.currentItem = 0
            }

            2 -> {
                binding.tvTemplate.setTextColor(activeColor)
                binding.tvTemplate.setDrawableTopWithTint(R.drawable.ic_template, activeColor)
                binding.viewPager2.currentItem = 1
            }
        }
    }
}
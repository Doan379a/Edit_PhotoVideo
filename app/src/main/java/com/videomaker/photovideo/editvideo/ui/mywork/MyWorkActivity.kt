package com.videomaker.photovideo.editvideo.ui.mywork

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.videomaker.photovideo.editvideo.MyApplication
import com.videomaker.photovideo.editvideo.R
import com.videomaker.photovideo.editvideo.base.BaseActivity
import com.videomaker.photovideo.editvideo.data.ImageData
import com.videomaker.photovideo.editvideo.databinding.ActivityMyWorkBinding
import com.videomaker.photovideo.editvideo.service.CreateVideoService
import com.videomaker.photovideo.editvideo.service.ImageCreatorService
import com.videomaker.photovideo.editvideo.ui.editmovie.ImageEditActivity
import com.videomaker.photovideo.editvideo.ui.editorimage.EditImageActivity
import com.videomaker.photovideo.editvideo.ui.main.MainActivity
import com.videomaker.photovideo.editvideo.utils.ImageUtils.getRealPathFromUri
import com.videomaker.photovideo.editvideo.utils.setDrawableStartWithTint
import com.videomaker.photovideo.editvideo.utils.setDrawableTopWithTint
import com.videomaker.photovideo.editvideo.widget.showSnackBar
import com.videomaker.photovideo.editvideo.widget.tap
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker

@AndroidEntryPoint
class MyWorkActivity : BaseActivity<ActivityMyWorkBinding>() {
    private lateinit var viewPagerAdapter: MyWorkAdapter
    private lateinit var application: MyApplication

    private var myPageChangeCallback: ViewPager2.OnPageChangeCallback =
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d("KKK", "onPageSelected: $position")
                setUpColorTab(position)
            }
        }

    override fun setViewBinding(): ActivityMyWorkBinding {
        return ActivityMyWorkBinding.inflate(layoutInflater)
    }

    override fun initView() {
        application = MyApplication.getInstance()
        viewPagerAdapter = MyWorkAdapter(this)
        binding.viewPager2.adapter = viewPagerAdapter
        binding.viewPager2.registerOnPageChangeCallback(myPageChangeCallback)
    }

    override fun viewListener() {
        binding.imgBack.tap {
            finish()
        }
        binding.tvVideo.setOnClickListener {
            setUpColorTab(0)
            binding.viewPager2.currentItem = 0
        }
        binding.tvPicture.setOnClickListener {
            setUpColorTab(1)
            binding.viewPager2.currentItem = 1
        }
        binding.tvNewProject.tap {
            if (binding.viewPager2.currentItem == 0) {
                TedImagePicker.with(this)

                    .cancelListener {
                        Log.d("TedImagePicker", "Người dùng đã hủy chọn ảnh")
                    }
                    .errorListener {
                        Log.d("TedImagePicker", "Lỗi khi chọn ảnh!")
                    }
                    .min(3, getString(R.string.Please_select_at_least_3_photos))
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
            } else {
                selectImageEdit()
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

    private fun getRealPathFromUri(context: Context, uri: Uri): String {
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
    override fun dataObservable() {

    }

    private fun selectImageEdit() {
        TedImagePicker.with(this)
            .cancelListener {
                Log.d("TedImagePicker", "Người dùng đã hủy chọn ảnh")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finishAffinity()
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

    private fun setUpColorTab(selectedTab: Int) = binding.apply {
        val activeColor = getColor(R.color.black)
        val inactiveColor = getColor(R.color.white)
        val activeBackGrColor = Color.parseColor("#A0E12E")
        val inactiveBackGrColor = Color.parseColor("#303030")
        binding.tvVideo.setTextColor(inactiveColor)
        binding.tvVideo.setDrawableStartWithTint(R.drawable.ic_video2, inactiveColor)
        binding.tvVideo.backgroundTintList = ColorStateList.valueOf(inactiveBackGrColor)
        binding.tvPicture.setTextColor(inactiveColor)
        binding.tvPicture.setDrawableStartWithTint(R.drawable.ic_selected_photo, inactiveColor)
        binding.tvPicture.backgroundTintList = ColorStateList.valueOf(inactiveBackGrColor)
        when (selectedTab) {
            0 -> {
                binding.tvVideo.setTextColor(activeColor)
                binding.tvVideo.setDrawableStartWithTint(R.drawable.ic_video2, activeColor)
                binding.tvVideo.backgroundTintList = ColorStateList.valueOf(activeBackGrColor)
            }

            1 -> {
                binding.tvPicture.setTextColor(activeColor)
                binding.tvPicture.setDrawableStartWithTint(
                    R.drawable.ic_selected_photo,
                    activeColor
                )
                binding.tvPicture.backgroundTintList = ColorStateList.valueOf(activeBackGrColor)

            }
        }
    }
}
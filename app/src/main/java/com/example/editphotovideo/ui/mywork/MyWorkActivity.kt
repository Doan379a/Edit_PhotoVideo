package com.example.editphotovideo.ui.mywork

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.editphotovideo.R
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.databinding.ActivityMyWorkBinding
import com.example.editphotovideo.ui.editorimage.EditImageActivity
import com.example.editphotovideo.ui.main.MainActivity
import com.example.editphotovideo.utils.setDrawableStartWithTint
import com.example.editphotovideo.utils.setDrawableTopWithTint
import com.example.editphotovideo.widget.showSnackBar
import com.example.editphotovideo.widget.tap
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker

@AndroidEntryPoint
class MyWorkActivity : BaseActivity<ActivityMyWorkBinding>() {
    private lateinit var viewPagerAdapter: MyWorkAdapter
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
                showSnackBar("video")
            } else {
                selectImageEdit()
            }
        }
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
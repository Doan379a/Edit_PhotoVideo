package com.videomaker.photovideo.editvideo.ui.main


import android.content.Intent
import com.videomaker.photovideo.editvideo.R
import com.videomaker.photovideo.editvideo.base.BaseActivity
import com.videomaker.photovideo.editvideo.databinding.ActivityMainBinding
import com.videomaker.photovideo.editvideo.ui.mywork.MyWorkActivity
import com.videomaker.photovideo.editvideo.ui.setting.SettingActivity

import com.videomaker.photovideo.editvideo.utils.setDrawableTopWithTint
import com.videomaker.photovideo.editvideo.widget.tap


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var viewPagerAdapter: MainAdapter

    override fun setViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }


    override fun initView() {
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
        binding.tvMyFolder.tap {
            startActivity(Intent(this, MyWorkActivity::class.java))
        }
        binding.imgSetting.tap {
            showActivity(SettingActivity::class.java)
        }
    }

    override fun dataObservable() {
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
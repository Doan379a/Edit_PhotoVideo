package com.example.editphotovideo.ui.main


import com.example.editphotovideo.R
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.data.ImageData
import com.example.editphotovideo.databinding.ActivityMainBinding
import com.example.editphotovideo.ui.editor.EditImageActivity
import com.example.editphotovideo.service.CreateVideoService
import com.example.editphotovideo.service.ImageCreatorService

import com.example.editphotovideo.ui.editmovie.MyApplication
import com.example.editphotovideo.ui.removebackgr.RemoveBackGrActivity
import com.example.editphotovideo.utils.setDrawableTopWithTint


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var viewPagerAdapter: MainAdapter
    private lateinit var application: MyApplication

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
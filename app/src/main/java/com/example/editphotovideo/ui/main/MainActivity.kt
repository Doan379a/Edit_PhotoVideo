package com.example.editphotovideo.ui.main

import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.databinding.ActivityMainBinding


class MainActivity :BaseActivity<ActivityMainBinding>() {
    override fun setViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
    }

    override fun viewListener() {
    }

    override fun dataObservable() {
    }

}
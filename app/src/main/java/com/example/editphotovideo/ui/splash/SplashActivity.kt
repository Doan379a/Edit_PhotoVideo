package com.example.editphotovideo.ui.splash

import android.annotation.SuppressLint
import com.example.editphotovideo.ui.language_start.LanguageStartActivity
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.databinding.ActivitySplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default);

    override fun setViewBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun initView() {
        coroutineScope.launch {
            delay(3000)
            showActivity(LanguageStartActivity::class.java)
        }
    }

    override fun viewListener() {

    }

    override fun dataObservable() {

    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {

    }

    override fun onResume() {
        super.onResume()
    }

}
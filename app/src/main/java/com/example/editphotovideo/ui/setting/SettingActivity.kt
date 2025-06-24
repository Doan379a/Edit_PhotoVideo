package com.example.editphotovideo.ui.setting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.content.ContextCompat.startActivity
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.databinding.ActivitySettingBinding
import com.example.editphotovideo.sharePreferent.PreferenceManager
import com.example.editphotovideo.sharePreferent.SharePrefUtils
import com.example.editphotovideo.ui.language.LanguageActivity
import com.example.editphotovideo.utils.helper.HelperMenu
import com.example.editphotovideo.widget.gone
import com.example.editphotovideo.widget.tap
import com.example.editphotovideo.widget.visible


class SettingActivity : BaseActivity<ActivitySettingBinding>(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var prefs: PreferenceManager

    private var helperMenu: HelperMenu? = null

    override fun setViewBinding(): ActivitySettingBinding {
        return ActivitySettingBinding.inflate(layoutInflater)
    }

    override fun initView() {
        prefs = PreferenceManager(this)
        if (SharePrefUtils.isRated(this))
            binding.tvRate.gone()

//        checkSwitch()
    }

    override fun viewListener() {
//        binding.swVibration.setOnCheckedChangeListener { _, isChecked ->
//            prefs.saveCheckVibration(isChecked)
//            checkSwitch()
//        }
//        binding.swSound.setOnCheckedChangeListener { _, isChecked ->
//            prefs.saveCheckSound(isChecked)
//            checkSwitch()
//        }
        binding.apply {
            tvRate.tap { helperMenu?.showDialogRate(false) }
            tvFeedback.tap { helperMenu?.showDialogFeedback() }
            tvShare.tap { helperMenu?.showShareApp() }
            tvPolicy.tap { helperMenu?.showPolicy() }
            tvLanguage.tap { showActivity(LanguageActivity::class.java) }
            ivBack.tap { finish() }
        }
    }

//    private fun checkSwitch() {
//        binding.swVibration.alpha = if (prefs.getCheckVibration()) 1f else 0.5f
//        binding.swSound.alpha = if (prefs.getCheckSound()) 1f else 0.5f
//    }

    override fun dataObservable() {
        helperMenu = HelperMenu(this)

        val prefs = getSharedPreferences("data", Context.MODE_PRIVATE)
        prefs.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == null)
            return

        if (SharePrefUtils.isRated(this))
            binding.tvRate.gone()
    }

}
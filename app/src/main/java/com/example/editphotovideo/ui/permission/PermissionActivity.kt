package  com.example.editphotovideo.ui.permission

import android.os.Bundle
import android.view.View
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.databinding.ActivityPermissionBinding
import com.example.editphotovideo.sharePreferent.SharePrefUtils
import com.example.editphotovideo.ui.main.MainActivity
import com.example.editphotovideo.utils.helper.Default.STORAGE_PERMISSION
import com.example.editphotovideo.widget.gone
import com.example.editphotovideo.widget.tap
import com.example.editphotovideo.widget.visible


class PermissionActivity : BaseActivity<ActivityPermissionBinding>() {


    override fun setViewBinding(): ActivityPermissionBinding {
        return ActivityPermissionBinding.inflate(layoutInflater)
    }

    override fun initView() {
        if (checkPermission(STORAGE_PERMISSION)) {
            allowCameraPermission()
        }

    }

    override fun viewListener() {
        binding.apply {
            ivSetCameraPermission.tap {
                showDialogPermission(STORAGE_PERMISSION)
            }
            tvContinue.tap {
                SharePrefUtils.forceGoToMain(this@PermissionActivity)
                showActivity(MainActivity::class.java)
                finishAffinity()
            }
        }

    }

    override fun dataObservable() {
    }

    override fun onPermissionGranted() {
        if (checkPermission(STORAGE_PERMISSION)) {
            allowCameraPermission()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onResume() {
        if (checkPermission(STORAGE_PERMISSION)) {
            allowCameraPermission()
        }
        super.onResume()
    }

    private fun allowCameraPermission() {
        binding.ivSetCameraPermission.gone()
        binding.ivSelectCameraPermission.visible()
    }

}
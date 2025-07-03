package  com.videomaker.photovideo.editvideo.ui.permission

import android.os.Bundle
import android.view.View
import com.videomaker.photovideo.editvideo.base.BaseActivity
import com.videomaker.photovideo.editvideo.databinding.ActivityPermissionBinding
import com.videomaker.photovideo.editvideo.sharePreferent.SharePrefUtils
import com.videomaker.photovideo.editvideo.ui.main.MainActivity
import com.videomaker.photovideo.editvideo.utils.helper.Default.STORAGE_PERMISSION
import com.videomaker.photovideo.editvideo.widget.gone
import com.videomaker.photovideo.editvideo.widget.tap
import com.videomaker.photovideo.editvideo.widget.visible


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
package com.example.editphotovideo.ui.tools

import android.content.Intent
import android.util.Log
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.databinding.ActivityToolsBinding
import com.example.editphotovideo.ui.editorimage.EditImageActivity
import com.example.editphotovideo.ui.tools.compressvideo.CompressVideoActivity
import com.example.editphotovideo.ui.tools.extract_audio.ExtractAudioActivity
import com.example.editphotovideo.ui.tools.reversevideo.ReverseVideoActivity
import com.example.editphotovideo.ui.tools.speed.SpeedActivity
import com.example.editphotovideo.ui.tools.trim.TrimActivity
import com.example.editphotovideo.widget.tap
import gun0912.tedimagepicker.builder.TedImagePicker

class ToolsActivity : BaseActivity<ActivityToolsBinding>() {

    override fun setViewBinding(): ActivityToolsBinding {
        return ActivityToolsBinding.inflate(layoutInflater)
    }

    override fun initView() {

    }

    override fun viewListener() {
        binding.imgBack.tap {
            finish()
        }
        binding.llCompressVideo.tap {
            selectVideo(1)
        }
        binding.llTrim.tap {
            selectVideo(2)
        }
        binding.llSpeed.tap {
            selectVideo(3)
        }
        binding.llReverseVideo.tap {
            selectVideo(4)
        }
        binding.llExtractAudio.tap {
            selectVideo(5)
        }
    }

    override fun dataObservable() {

    }

    private fun selectVideo(int: Int) {
        TedImagePicker.with(this)
            .video()
            .cancelListener {
                Log.d("TedImagePicker", "Người dùng đã hủy chọn ảnh")
            }
            .errorListener {
                Log.d("TedImagePicker", "Lỗi khi chọn video!")
            }
            .start { uri ->
                val nameClass = when (int) {
                    1 -> CompressVideoActivity::class.java
                    2 -> TrimActivity::class.java
                    3 -> SpeedActivity::class.java
                    4-> ReverseVideoActivity::class.java
                    5-> ExtractAudioActivity::class.java
                    else -> {
                        CompressVideoActivity::class.java
                    }
                }
                val intent = Intent(this, nameClass).apply {
                    putExtra(
                        "URI_VIDEO_INPUT",
                        uri.toString()
                    )
                }

                startActivity(intent)
            }
    }
}
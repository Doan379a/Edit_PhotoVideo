package com.videomaker.photovideo.editvideo.ui.tools.extract_audio

import android.app.Activity
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.videomaker.photovideo.editvideo.R
import com.videomaker.photovideo.editvideo.base.BaseActivity
import com.videomaker.photovideo.editvideo.data.entity.MediaEntity
import com.videomaker.photovideo.editvideo.databinding.ActivityResultAudioBinding
import com.videomaker.photovideo.editvideo.utils.ImageUtils.DEFAULT_FOLDER
import com.videomaker.photovideo.editvideo.utils.ShareUtils
import com.videomaker.photovideo.editvideo.utils.VideoUtils.buildDeleteIntentSender
import com.videomaker.photovideo.editvideo.utils.VideoUtils.getDisplayNameFromUri
import com.videomaker.photovideo.editvideo.utils.ViewUtils.formatTime
import com.videomaker.photovideo.editvideo.widget.tap
import com.videomaker.photovideo.editvideo.widget.visible
import gun0912.tedimagepicker.util.ToastUtil.showToast
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.TimeUnit

class ResultAudioActivity : BaseActivity<ActivityResultAudioBinding>() {

    private var mediaPlayer: MediaPlayer? = null
    private val handler = Handler(Looper.getMainLooper())
    private var isTracking = false
    private var isPlaying = false
    private var uri: Uri? = null
    private lateinit var deleteLauncher: ActivityResultLauncher<IntentSenderRequest>

    override fun setViewBinding(): ActivityResultAudioBinding {
        return ActivityResultAudioBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val audioUri = intent.getStringExtra("AUDIO_URI") ?: return
        uri = Uri.parse(audioUri)
        if (uri != null) {
            mediaPlayer = MediaPlayer.create(this, uri)
            initMediaPlayer()
            val name = getDisplayNameFromUri(this, uri!!)
            binding.tvtLinkPath.text = "Music/$DEFAULT_FOLDER/${name ?: audioUri}"
        }
        deleteLauncher =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    showToast("xoa thanh cong ")
                    finish()
                } else {
                }
            }

    }

    private fun initMediaPlayer() {
        mediaPlayer?.setOnPreparedListener {
            binding.tvEnd.text = formatTime(it.duration)
            binding.seekBar.max = 100
        }

        mediaPlayer?.setOnCompletionListener {
            isPlaying = false
            binding.btnPlayPause.setImageResource(com.videomaker.photovideo.editvideo.R.drawable.ic_play)
            binding.seekBar.progress = 0
            handler.removeCallbacks(updateRunnable)
        }
    }

    override fun viewListener() {
        binding.imgDelete.tap {

            uri?.let { it1 ->
                buildDeleteIntentSender(this, it1) { request, deleted ->
                    if (deleted) {
                        lifecycleScope.launch {
                            Toast.makeText(
                                this@ResultAudioActivity,
                                "Đã xóa thành công",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        finish()
                    } else if (request != null) {
                        deleteLauncher.launch(request)
                    } else {
                        Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
        binding.imgShareMore.tap {
            ShareUtils.shareAudio(
                this,
                ShareUtils.KeyShare.SHARE_MORE,
                Uri.parse(intent.getStringExtra("AUDIO_URI"))
            )
        }
        binding.btnPlayPause.tap {
            if (isPlaying) {
                pauseAudio()
            } else {
                playAudio()
            }
        }

        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser && mediaPlayer != null && mediaPlayer!!.isPlaying) {
                    val newPos = (mediaPlayer!!.duration * progress) / 100
                    mediaPlayer!!.seekTo(newPos)
                }
            }

            override fun onStartTrackingTouch(sb: SeekBar?) {
                isTracking = true
            }

            override fun onStopTrackingTouch(sb: SeekBar?) {
                isTracking = false
            }
        })
    }

    private fun playAudio() {
        mediaPlayer?.start()
        isPlaying = true
        binding.btnPlayPause.setImageResource(com.videomaker.photovideo.editvideo.R.drawable.ic_pause)
        updateSeekBar()
    }

    private fun pauseAudio() {
        mediaPlayer?.pause()
        isPlaying = false
        binding.btnPlayPause.setImageResource(com.videomaker.photovideo.editvideo.R.drawable.ic_play)
        handler.removeCallbacks(updateRunnable)
    }

    private fun updateSeekBar() {
        handler.post(updateRunnable)
    }

    private val updateRunnable = object : Runnable {
        override fun run() {
            mediaPlayer?.let {
                if (!isTracking) {
                    val currentPos = it.currentPosition
                    val duration = it.duration
                    if (duration > 0) {
                        binding.seekBar.progress = (currentPos * 100) / duration
                        binding.tvStart.text = formatTime(currentPos)
                    }
                }
                handler.postDelayed(this, 500)
            }
        }
    }

    private fun formatTime(ms: Int): String {
        val min = TimeUnit.MILLISECONDS.toMinutes(ms.toLong())
        val sec = TimeUnit.MILLISECONDS.toSeconds(ms.toLong()) % 60
        return String.format("%02d:%02d", min, sec)
    }

    override fun onPause() {
        super.onPause()
        pauseAudio()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        handler.removeCallbacks(updateRunnable)
    }

    override fun dataObservable() {}
}

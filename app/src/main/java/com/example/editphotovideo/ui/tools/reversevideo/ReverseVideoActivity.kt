package com.example.editphotovideo.ui.tools.reversevideo

import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.editphotovideo.R
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.data.viewmodel.MediaViewModel
import com.example.editphotovideo.databinding.ActivityReverseVideoBinding
import com.example.editphotovideo.libffmpeg.FFmpeg
import com.example.editphotovideo.utils.ViewUtils.formatTime
import com.example.editphotovideo.widget.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import com.arthenica.ffmpegkit.FFmpegKit
import com.arthenica.ffmpegkit.ReturnCode
import com.example.editphotovideo.widget.tap

@AndroidEntryPoint
class ReverseVideoActivity:BaseActivity<ActivityReverseVideoBinding>() {
    private var videoUri: String? = null
    private var isPlaying = false
    private var isTracking = false
    private val handler = Handler(Looper.getMainLooper())
    private  val mediaViewModel: MediaViewModel by viewModels()

    private val updateRunnable = object : Runnable {
        override fun run() {
            if (binding.videoView.isPlaying && !isTracking) {
                val position = binding.videoView.currentPosition
                val duration = binding.videoView.duration
                if (duration > 0) {
                    val progress = (position * 100) / duration
                    binding.seekBar.progress = progress
                    binding.tvStart.text = formatTime(position)
                }
            }
            handler.postDelayed(this, 500)
        }
    }

    override fun setViewBinding(): ActivityReverseVideoBinding {
        return ActivityReverseVideoBinding.inflate(layoutInflater)
    }

    override fun initView() {
        videoUri = intent.getStringExtra("URI_VIDEO_INPUT")
        if (videoUri != null) {
            Log.d("URI_VIDEO_INPUT", videoUri!!)
            setupVideoView(videoUri!!)
        }
    }

    override fun viewListener() {
        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.parent.setOnClickListener {
            togglePlayPause()
        }
        binding.btnPlayPause.setOnClickListener { togglePlayPause() }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser && binding.videoView.duration > 0) {
                    val position = (binding.videoView.duration * progress) / 100
                    binding.videoView.seekTo(position)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                isTracking = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                isTracking = false
            }
        })
        binding.tvReverseVideo.tap {
            if (videoUri == null) return@tap

            val inputPath = Uri.parse(videoUri).path ?: return@tap
            val output = File(getExternalFilesDir(null), "reversed_${System.currentTimeMillis()}.mp4")

            showLoading(true)

            lifecycleScope.launch(Dispatchers.IO) {
                val cmd = listOf(
                    "-i", "\"$inputPath\"",
                    "-vf", "reverse",
                    "-af", "areverse",
                    "-preset", "ultrafast",
                    "\"${output.absolutePath}\""
                ).joinToString(" ")

                val session = FFmpegKit.execute(cmd)

                withContext(Dispatchers.Main) {
                    showLoading(false)
                    if (ReturnCode.isSuccess(session.returnCode)) {
                        binding.videoView.setVideoURI(Uri.fromFile(output))
                        binding.videoView.start()
                        isPlaying = true
                        Toast.makeText(this@ReverseVideoActivity, "Đã phát video ngược", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@ReverseVideoActivity, "Lỗi đảo ngược: ${session.failStackTrace}", Toast.LENGTH_SHORT).show()
                        Log.e("ReverseVideoActivity", "Lỗi đảo ngược: ${session.failStackTrace}")
                    }
                }
            }
        }


    }

    override fun dataObservable() {
    }
    private fun setupVideoView(videoPath: String) {
        Log.d("ItemVideoPlayerFragment", "Initializing video: $videoPath")
        binding.videoView.setVideoURI(Uri.parse(videoUri))

        binding.videoView.setOnPreparedListener { mp ->
            val videoWidth = mp.videoWidth
            val videoHeight = mp.videoHeight
            val containerWidth = binding.parent.width
            if (videoWidth > 0 && videoHeight > 0) {
                val calculatedHeight = containerWidth * videoHeight / videoWidth
                binding.videoView.layoutParams = binding.videoView.layoutParams.apply {
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                    height = calculatedHeight
                }
            }

            binding.videoView.start()
            binding.btnPlayPause.setImageResource(R.drawable.ic_pause)
            isPlaying = true
            binding.tvEnd.text = formatTime(mp.duration)
            updateSeekBar()
        }

        binding.videoView.setOnCompletionListener {
            isPlaying = false
            binding.btnPlayPause.setImageResource(R.drawable.ic_play)
            binding.btnPlayPause.visible()
            binding.seekBar.progress = 0
        }
    }

    private fun updateSeekBar() {
        handler.post(updateRunnable)
    }

    private fun showLoading(show: Boolean) {
        binding.loadingProgress.visibility = if (show) View.VISIBLE else View.GONE
        binding.tvSave.isEnabled = !show
    }

    private fun togglePlayPause() {
        if (isPlaying) {
            pauseVideo()
        } else {
            playVideo()
        }
    }

    private fun playVideo() {
        binding.videoView.start()
        binding.btnPlayPause.setImageResource(R.drawable.ic_pause)
        isPlaying = true
        binding.btnPlayPause.visible()
        updateSeekBar()
    }

    private fun pauseVideo() {
        binding.videoView.pause()
        binding.btnPlayPause.setImageResource(R.drawable.ic_play)
        binding.btnPlayPause.visibility = View.VISIBLE
        isPlaying = false

    }
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateRunnable)
        pauseVideo()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(updateRunnable)
        pauseVideo()
    }
}
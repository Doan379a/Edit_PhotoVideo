package com.videomaker.photovideo.editvideo.ui.tools.extract_audio

import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.arthenica.ffmpegkit.FFmpegKit
import com.arthenica.ffmpegkit.FFmpegKitConfig
import com.arthenica.ffmpegkit.ReturnCode
import com.videomaker.photovideo.editvideo.R
import com.videomaker.photovideo.editvideo.base.BaseActivity
import com.videomaker.photovideo.editvideo.data.viewmodel.MediaViewModel
import com.videomaker.photovideo.editvideo.databinding.ActivityExtractAudioBinding
import com.videomaker.photovideo.editvideo.ui.main.MainActivity
import com.videomaker.photovideo.editvideo.ui.save.KeyNewProject
import com.videomaker.photovideo.editvideo.utils.ImageUtils.getTempMovieDir
import com.videomaker.photovideo.editvideo.utils.VideoUtils.saveAudioToMusic
import com.videomaker.photovideo.editvideo.utils.ViewUtils.createSeekBarChangeListener
import com.videomaker.photovideo.editvideo.utils.ViewUtils.formatTime
import com.videomaker.photovideo.editvideo.utils.ViewUtils.showLoadingView
import com.videomaker.photovideo.editvideo.widget.showToast
import com.videomaker.photovideo.editvideo.widget.tap
import com.videomaker.photovideo.editvideo.widget.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@AndroidEntryPoint
class ExtractAudioActivity:BaseActivity<ActivityExtractAudioBinding>() {
    private var videoUri: String? = null
    private var isPlaying = false
    private var isTracking = false
    private val handler = Handler(Looper.getMainLooper())
    private  val mediaViewModel: MediaViewModel by viewModels()
    private var isDialogShowing = false

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

    override fun setViewBinding(): ActivityExtractAudioBinding {
        return ActivityExtractAudioBinding.inflate(layoutInflater)
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
        binding.seekBar.setOnSeekBarChangeListener(
            createSeekBarChangeListener(
                videoView = binding.videoView,
                onStart = { isTracking = true },
                onStop = { isTracking = false }
            )
        )

        binding.tvExtractAudio.tap {
            extractAudio()
        }
    }

    override fun dataObservable() {
    }
    private fun extractAudio() {
        if (videoUri == null) return

        val inputUri = Uri.parse(videoUri!!)
        val tempInput = File(cacheDir, "temp_input.mp4")
        val fileName = "Audio_${System.currentTimeMillis()}.mp3"
        val tempOutput = File(cacheDir, fileName)

        showLoadingView(
            loadingView = binding.loadingProgress,
            show = true
        )
        handler.removeCallbacks(updateRunnable)
        pauseVideo()
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                contentResolver.openInputStream(inputUri)?.use { input ->
                    tempInput.outputStream().use { output -> input.copyTo(output) }
                }
                val cmd = "-y -i ${tempInput.absolutePath} -vn -acodec libmp3lame ${tempOutput.absolutePath}"
                val session = FFmpegKit.execute(cmd)

                Log.d("ExtractAudio", "Command: ${session.command}")
                Log.d("ExtractAudio", "Output: ${session.output}")

                val success = ReturnCode.isSuccess(session.returnCode)

                withContext(Dispatchers.Main) {
                    showLoadingView(
                        loadingView = binding.loadingProgress,
                        show = false
                    )
                    if (success && tempOutput.exists()) {
                        val saved = saveAudioToMusic(this@ExtractAudioActivity,tempOutput, fileName)
                        if (saved.second) {
//                            showToast("Đã tách và lưu vào Music/ExtractedAudios")
                            val intent = Intent(this@ExtractAudioActivity, ResultAudioActivity::class.java)
                            intent.putExtra("AUDIO_URI", saved.first.toString())
                            intent.putExtra("KEY_ACTIVITY", KeyNewProject.EXTRACT_ACTIVITY.name)
                            startActivity(intent)
                            finish()
                        } else {
//                            showToast("Tách OK nhưng lưu thất bại")
                        }
                    } else {
//                        showToast("Tách âm thanh thất bại")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showLoadingView(
                        loadingView = binding.loadingProgress,
                        show = false
                    )
                    showToast("Lỗi: ${e.message}")
                    Log.e("ExtractAudio", "Exception", e)
                }
            }
        }
    }

    private fun setupVideoView(videoPath: String) {
        Log.d("ItemVideoPlayerFragment", "Initializing video: $videoPath")
        binding.videoView.setVideoURI(Uri.parse(videoUri))

        binding.videoView.setOnPreparedListener { mp ->


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
    override fun onBackPressed() {
        if (binding.loadingProgress.visibility == View.VISIBLE && !isDialogShowing) {
            showSaveDialog()
        } else {
            super.onBackPressed()
        }
    }

    private fun showSaveDialog() {
        isDialogShowing = true
        com.videomaker.photovideo.editvideo.dialog.AlertDialog(this) {
            isDialogShowing = false
            finish()
        }.apply {
            setOnDismissListener { isDialogShowing = false }
            show()
        }
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
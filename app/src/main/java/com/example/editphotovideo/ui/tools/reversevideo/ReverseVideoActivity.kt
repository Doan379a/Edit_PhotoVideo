package com.example.editphotovideo.ui.tools.reversevideo

import android.content.Intent
import android.media.MediaScannerConnection
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
import com.example.editphotovideo.utils.ViewUtils.formatTime
import com.example.editphotovideo.widget.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import com.arthenica.ffmpegkit.FFmpegKit
import com.arthenica.ffmpegkit.FFmpegKitConfig
import com.arthenica.ffmpegkit.ReturnCode
import com.example.editphotovideo.data.entity.MediaEntity
import com.example.editphotovideo.data.entity.MediaType
import com.example.editphotovideo.ui.main.MainActivity
import com.example.editphotovideo.ui.save.KeyNewProject
import com.example.editphotovideo.ui.save.SaveVideoActivity
import com.example.editphotovideo.utils.ImageUtils.getTempMovieDir
import com.example.editphotovideo.utils.ViewUtils.createSeekBarChangeListener
import com.example.editphotovideo.utils.ViewUtils.showLoadingView
import com.example.editphotovideo.widget.getTagDebug
import com.example.editphotovideo.widget.showToast
import com.example.editphotovideo.widget.tap

@AndroidEntryPoint
class ReverseVideoActivity : BaseActivity<ActivityReverseVideoBinding>() {
    private var videoUri: String? = null
    private var isPlaying = false
    private var isTracking = false
    private val handler = Handler(Looper.getMainLooper())
    private val mediaViewModel: MediaViewModel by viewModels()
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

    override fun setViewBinding(): ActivityReverseVideoBinding {
        return ActivityReverseVideoBinding.inflate(layoutInflater)
    }

    override fun initView() {
        videoUri = intent.getStringExtra("URI_VIDEO_INPUT")
        if (videoUri != null) {
            Log.d("URI_VIDEO_INPUT", videoUri!!)
            setupVideoView(videoUri!!)
        }
        FFmpegKitConfig.enableLogCallback { log ->
            Log.d("FFmpegLog", log.message)
        }

    }

    override fun viewListener() {
        binding.imgBack.setOnClickListener {
            showActivity(MainActivity::class.java)
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

        binding.tvReverseVideo.tap {
            reverseVideo()
        }

    }

    override fun dataObservable() {
    }

    private fun reverseVideo() {
        if (videoUri == null) return

        val inputPath = Uri.parse(videoUri) ?: return
        val output = File(getTempMovieDir(this@ReverseVideoActivity), "Reversed_${System.currentTimeMillis()}.mp4")

        showLoadingView(
            loadingView = binding.loadingProgress,
            show = true
        )

        handler.removeCallbacks(updateRunnable)
        pauseVideo()
        lifecycleScope.launch(Dispatchers.IO) {
            val inputStream = contentResolver.openInputStream(inputPath)
            val tempFile = File(cacheDir, "temp_input.mp4")
            tempFile.outputStream().use { output -> inputStream?.copyTo(output) }
            val cmd = "-y -i ${tempFile.absolutePath} -vf reverse -af areverse -c:v mpeg4 -b:v 2M -c:a aac ${output.absolutePath}"

            val session = FFmpegKit.execute(cmd)
            Log.d(getTagDebug(), "Command: ${session.command}")
            Log.d(getTagDebug(), "State: ${session.state}")
            Log.d(getTagDebug(), "ReturnCode: ${session.returnCode}")
            Log.d(getTagDebug(), "Output:\n${session.output}")
            Log.d(getTagDebug(), "FailStackTrace:\n${session.failStackTrace}")
            withContext(Dispatchers.Main) {
                showLoadingView(
                    loadingView = binding.loadingProgress,
                    show = false
                )

                if (ReturnCode.isSuccess(session.returnCode)) {
                    MediaScannerConnection.scanFile(
                        this@ReverseVideoActivity,
                        arrayOf(output.path),
                        arrayOf("video/mp4"),
                        null
                    )
                    val entity = MediaEntity(
                        filePath = output.path,
                        mediaType = MediaType.VIDEO
                    )
                    mediaViewModel.insertMedia(entity)
                    val intent = Intent(this@ReverseVideoActivity, SaveVideoActivity::class.java)
                    intent.putExtra("URI_VIDEO_INPUT", output.path)
                    intent.putExtra("KEY_ACTIVITY", KeyNewProject.REVERSE_ACTIVITY.name)
                    startActivity(intent)
                    finish()
//                    showToast( "speed xong: ${output.path}")
                    Log.d("ItemVideoPlayerFragment", "Video processed: $output.path")
//                    showToast( "Đã phát video ngược")
                } else {
//                    showToast("Lỗi đảo ngược: ${session.failStackTrace}")
                    showLoadingView(
                        loadingView = binding.loadingProgress,
                        show = true
                    )

                    Log.e("ReverseVideoActivity", "Lỗi đảo ngược: ${session.failStackTrace}")
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
        com.example.editphotovideo.dialog.AlertDialog(this) {
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
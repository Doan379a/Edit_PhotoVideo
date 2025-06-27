package com.example.editphotovideo.ui.tools.speed

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.editphotovideo.R
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.data.entity.MediaEntity
import com.example.editphotovideo.data.entity.MediaType
import com.example.editphotovideo.data.viewmodel.MediaViewModel
import com.example.editphotovideo.databinding.ActivitySpeedBinding
import com.example.editphotovideo.ui.main.MainActivity
import com.example.editphotovideo.ui.save.KeyNewProject
import com.example.editphotovideo.ui.save.SaveVideoActivity
import com.example.editphotovideo.utils.ImageUtils.getRealPathFromUri
import com.example.editphotovideo.utils.ImageUtils.getTempMovieDir
import com.example.editphotovideo.utils.ViewUtils.formatTime
import com.example.editphotovideo.utils.ViewUtils.showLoadingView
import com.example.editphotovideo.widget.showToast
import com.example.editphotovideo.widget.tap
import com.example.editphotovideo.widget.visible
import com.hw.videoprocessor.VideoProcessor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@AndroidEntryPoint
class SpeedActivity : BaseActivity<ActivitySpeedBinding>() {
    private var videoUri: String? = null
    private var isPlaying = false
    private var isTracking = false
    private var videoDuration = 0
    private val handler = Handler(Looper.getMainLooper())
    private val speeds = listOf(0.5f, 0.75f, 1.0f, 1.5f, 2.0f)
    private var speed = 1f
    private var mediaPlayer: MediaPlayer? = null
    private val mediaViewModel: MediaViewModel by viewModels()
    private var isDialogShowing = false

    private val updateRunnable = object : Runnable {
        override fun run() {
            if (binding.videoView.isPlaying && !isTracking) {
                val position = binding.videoView.currentPosition
                if (videoDuration > 0) {
                    val progress = (position * 100) / videoDuration
                    binding.seekBar.progress = progress
                    binding.tvStart.text = formatTime(position)
                }
            }
            handler.postDelayed(this, 500)
        }
    }

    override fun setViewBinding(): ActivitySpeedBinding =
        ActivitySpeedBinding.inflate(layoutInflater)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        videoUri = intent.getStringExtra("URI_VIDEO_INPUT")
        videoUri?.let {
            Log.d("URI_VIDEO_INPUT", it)
            setupVideoView(it)
        }

        val thumbDrawable = ContextCompat.getDrawable(this, R.drawable.ic_thumb_seekbar3)
        binding.speedSlider.apply {
            thumbRadius = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp)
            thumbDrawable?.let { setCustomThumbDrawable(it) }
        }

        setUpdateSpeedLabels(1f)
        setUpSeekbar()
        setUpSpeedSlider()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun viewListener() {
        binding.imgBack.tap {
            showActivity(MainActivity::class.java)
            finish()
        }
        binding.parent.setOnClickListener { togglePlayPause() }
        binding.btnPlayPause.setOnClickListener { togglePlayPause() }
        binding.tvSave.tap { speedVideo(speed) }
    }

    override fun dataObservable() {

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpSpeedSlider() {
        binding.speedSlider.addOnChangeListener { _, value, _ ->
            val index = value.toInt().coerceIn(0, speeds.lastIndex)
            speed = speeds[index]
            setUpdateSpeedLabels(speed)
            Log.d("SpeedSlider", "Speed selected: ${speed}x")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mediaPlayer?.let {
                    val params = it.playbackParams
                    params.speed = speed
                    it.playbackParams = params
                }
            }
        }
    }

    private fun setUpSeekbar() =
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser && videoDuration > 0) {
                    val position = (videoDuration * progress) / 100
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

    private fun setupVideoView(videoPath: String) {
        binding.videoView.setVideoURI(Uri.parse(videoPath))

        binding.videoView.setOnPreparedListener { mp ->

            mediaPlayer = mp
            videoDuration = mp.duration
            binding.tvEnd.text = formatTime(videoDuration)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val params = mp.playbackParams
                params.speed = speed
                mp.playbackParams = params
            }

            mp.start()
            binding.btnPlayPause.setImageResource(R.drawable.ic_pause)
            isPlaying = true
            updateSeekBar()
        }

        binding.videoView.setOnCompletionListener {
            isPlaying = false
            binding.btnPlayPause.setImageResource(R.drawable.ic_play)
            binding.btnPlayPause.visible()
            binding.seekBar.progress = 0
        }
    }

    private fun togglePlayPause() {
        if (isPlaying) pauseVideo() else playVideo()
    }

    private fun playVideo() {
        binding.videoView.start()
        binding.btnPlayPause.setImageResource(R.drawable.ic_pause)
        isPlaying = true
        updateSeekBar()
    }

    private fun pauseVideo() {
        binding.videoView.pause()
        binding.btnPlayPause.setImageResource(R.drawable.ic_play)
        binding.btnPlayPause.visibility = View.VISIBLE
        isPlaying = false
    }

    private fun updateSeekBar() {
        handler.post(updateRunnable)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpdateSpeedLabels(float: Float) {
        val colorUnSelected = Color.parseColor("#797979")
        val colorSelected = Color.parseColor("#FFFFFF")
        val fontSelected = R.font.polly_bold_700
        val fontUnSelected = R.font.polly_regular_400
        val sizeSelected = 16f
        val sizeUnSelected = 12f

        val labels = listOf(
            binding.tv05X to 0.5f,
            binding.tv075X to 0.75f,
            binding.tv1X to 1.0f,
            binding.tv15X to 1.5f,
            binding.tv2X to 2.0f
        )

        labels.forEach { (label, value) ->
            val isSelected = float == value
            label.setTextColor(if (isSelected) colorSelected else colorUnSelected)
            label.textSize = if (isSelected) sizeSelected else sizeUnSelected
            label.typeface = resources.getFont(if (isSelected) fontSelected else fontUnSelected)
        }
    }

    private fun speedVideo(speed: Float) {
        showLoadingView(
            loadingView = binding.loadingProgress,
            show = true
        )
        handler.removeCallbacks(updateRunnable)
        pauseVideo()
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val moviesDir = getTempMovieDir(this@SpeedActivity)
                    val filePrefix = "Speed_${System.currentTimeMillis()}"
                    val fileExtn = ".mp4"
                    var dest = File(moviesDir, "$filePrefix$fileExtn")
                    var fileNo = 0
                    while (dest.exists()) {
                        fileNo++
                        dest = File(moviesDir, "$filePrefix$fileNo$fileExtn")
                    }
                    val filePath = dest.absolutePath

                    val realPath = getRealPathFromUri(this@SpeedActivity, Uri.parse(videoUri))
                        ?: return@withContext

                    VideoProcessor.processor(this@SpeedActivity)
                        .input(realPath)
                        .output(filePath)
                        .speed(speed)
                        .changeAudioSpeed(true)
                        .process()

                    withContext(Dispatchers.Main) {
                        MediaScannerConnection.scanFile(
                            this@SpeedActivity,
                            arrayOf(filePath),
                            arrayOf("video/mp4"),
                            null
                        )
                        val entity = MediaEntity(
                            filePath = filePath,
                            mediaType = MediaType.VIDEO
                        )
                        mediaViewModel.insertMedia(entity)
                        showLoadingView(
                            loadingView = binding.loadingProgress,
                            show = false
                        )

                        val intent = Intent(this@SpeedActivity, SaveVideoActivity::class.java)
                        intent.putExtra("URI_VIDEO_INPUT", filePath)
                        intent.putExtra("KEY_ACTIVITY", KeyNewProject.SPEED_ACTIVITY.name)
                        startActivity(intent)
                        finish()
//                        showToast("speed xong: ${filePath}")
                        Log.d("ItemVideoPlayerFragment", "Video processed: $filePath")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    withContext(Dispatchers.Main) {
                        showLoadingView(
                            loadingView = binding.loadingProgress,
                            show = false
                        )

                    }
                }
            }
        }
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
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(updateRunnable)
        pauseVideo()
    }
}

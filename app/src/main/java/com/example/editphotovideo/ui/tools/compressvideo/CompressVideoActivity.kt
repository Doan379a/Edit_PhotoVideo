package com.example.editphotovideo.ui.tools.compressvideo

import android.media.MediaMetadataRetriever
import android.media.MediaMetadataRetriever.METADATA_KEY_DURATION
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.editphotovideo.R
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.databinding.ActivityCompressVideoBinding
import com.example.editphotovideo.utils.ImageUtils.getRealPathFromUri
import com.example.editphotovideo.utils.ImageUtils.getTempMovieDir
import com.example.editphotovideo.widget.tap
import com.example.editphotovideo.widget.visible
import com.hw.videoprocessor.VideoProcessor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class CompressVideoActivity : BaseActivity<ActivityCompressVideoBinding>() {
    private var videoUri: String? = null
    private var isPlaying = false
    private var isTracking = false
    private val handler = Handler(Looper.getMainLooper())
    private var durationSec: Long = 0
    private var originWidth: Int = 0
    private var originHeight: Int = 0
    private var originBitrate: Int = 0

    override fun setViewBinding(): ActivityCompressVideoBinding {
        return ActivityCompressVideoBinding.inflate(layoutInflater)
    }

    override fun initView() {
        videoUri = intent.getStringExtra("URI_VIDEO_INPUT")
        if (videoUri != null) {
            Log.d("URI_VIDEO_INPUT", videoUri!!)
            setupVideoView(videoUri!!)
        }
        getVideoMetadata(Uri.parse(videoUri!!))
    }

    override fun viewListener() {
        binding.imgBack.tap { finish() }
        binding.tvCompress.tap {
            CompressBottomSheet(
                this@CompressVideoActivity,
                durationSec,
                originWidth,
                originHeight,
                originBitrate
            ) { w, h, bitrate ->
                executeScaleVideo(w, h, bitrate, 0, (durationSec * 1000).toInt())
            }.show(supportFragmentManager, "CompressBottomSheet")
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
    }

    private fun executeScaleVideo(
        outWidth: Int,
        outHeight: Int,
        bitrate: Int,
        startMs: Int,
        endMs: Int
    ) {
        showLoading(true)

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val moviesDir = getTempMovieDir()
                    val filePrefix = "Compressed_${System.currentTimeMillis()}"
                    val fileExtn = ".mp4"
                    var dest = File(moviesDir, "$filePrefix$fileExtn")
                    var fileNo = 0
                    while (dest.exists()) {
                        fileNo++
                        dest = File(moviesDir, "$filePrefix$fileNo$fileExtn")
                    }
                    val filePath = dest.absolutePath

                    val realPath =
                        getRealPathFromUri(this@CompressVideoActivity, Uri.parse(videoUri))
                            ?: return@withContext

                    VideoProcessor.processor(applicationContext)
                        .input(realPath)
                        .output(filePath)
                        .outWidth(outWidth)
                        .outHeight(outHeight)
                        .startTimeMs(startMs)
                        .endTimeMs(endMs)
                        .bitrate(bitrate)
                        .process()

                    withContext(Dispatchers.Main) {
//                        startPreviewActivity(filePath)
                        MediaScannerConnection.scanFile(
                            this@CompressVideoActivity,
                            arrayOf(filePath),
                            arrayOf("video/mp4"),
                            null
                        )
                        Log.d("ItemVideoPlayerFragment", "Video processed: $filePath")
                        Toast.makeText(
                            this@CompressVideoActivity,
                            "Xử lý video xong!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
//                        postError()
                    }
                } finally {
                    withContext(Dispatchers.Main) {
                        showLoading(false)
                    }
                }
            }
        }
    }


    override fun dataObservable() {
    }

    private fun getVideoMetadata(uri: Uri) {
        val retriever = MediaMetadataRetriever().apply {
            setDataSource(this@CompressVideoActivity, uri)
        }
        durationSec = retriever.extractMetadata(METADATA_KEY_DURATION)?.toLong()?.div(1000) ?: 0L
        originWidth =
            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)?.toInt() ?: 0
        originHeight =
            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)?.toInt()
                ?: 0
        originBitrate =
            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE)?.toInt() ?: 0
        retriever.release()

    }

    private fun showLoading(show: Boolean) {
        binding.loadingProgress.visibility = if (show) View.VISIBLE else View.GONE
        binding.tvCompress.isEnabled = !show
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

    private fun updateSeekBar() {
        handler.post(updateRunnable)
    }

    private fun formatTime(millis: Int): String {
        val totalSeconds = millis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
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
package com.example.editphotovideo.ui.save

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.editphotovideo.R
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.databinding.ActivitySaveImageBinding
import com.example.editphotovideo.databinding.ActivitySaveVideoBinding
import com.example.editphotovideo.ui.editorimage.EditImageActivity
import com.example.editphotovideo.ui.main.MainActivity
import com.example.editphotovideo.utils.ShareImage
import com.example.editphotovideo.utils.ShareImage.shareVideo
import com.example.editphotovideo.utils.ViewUtils.formatTime
import com.example.editphotovideo.widget.tap
import com.example.editphotovideo.widget.visible
import gun0912.tedimagepicker.builder.TedImagePicker


class SaveVideoActivity : BaseActivity<ActivitySaveVideoBinding>() {
    private var videoUri: String? = null
    private var isPlaying = false
    private var isTracking = false
    private val handler = Handler(Looper.getMainLooper())

    override fun setViewBinding(): ActivitySaveVideoBinding {
        return ActivitySaveVideoBinding.inflate(layoutInflater)
    }

    override fun initView() {
        videoUri = intent.getStringExtra("URI_VIDEO_INPUT")
        if (videoUri != null) {
            Log.d("URI_VIDEO_INPUT", videoUri!!)
            setupVideoView(videoUri!!)
        }
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

    override fun viewListener() {
        binding.imgBack.tap {
            finish()
        }
        binding.parent.setOnClickListener {
            togglePlayPause()
        }
        binding.btnPlayPause.setOnClickListener { togglePlayPause() }
        binding.imgHome.tap {
            showActivity(MainActivity::class.java)
        }
        binding.imgFaceBook.tap {
            shareVideo(this, ShareImage.KeyShare.FACEBOOK, Uri.parse(videoUri))
        }
        binding.imgShareMore.tap {
            shareVideo(this, ShareImage.KeyShare.SHARE_MORE, Uri.parse(videoUri))
        }
        binding.imgInstagram.tap {
            shareVideo(this, ShareImage.KeyShare.INSTAGRAM, Uri.parse(videoUri))
        }
        binding.imgTiktok.tap {
            shareVideo(this, ShareImage.KeyShare.TIKTOK, Uri.parse(videoUri))
        }
        binding.imgYoutube.tap {
            shareVideo(this, ShareImage.KeyShare.YOUTUBE, Uri.parse(videoUri))
        }
        binding.imgMessenger.tap {
            shareVideo(this, ShareImage.KeyShare.MESSENGER, Uri.parse(videoUri))
        }
        binding.imgWhatsapp.tap {
            shareVideo(this, ShareImage.KeyShare.WHATSAPP, Uri.parse(videoUri))
        }
        binding.tvNewProject.tap { selectImageEdit() }
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

    private fun selectImageEdit() {
        TedImagePicker.with(this)
            .cancelListener {
                Log.d("TedImagePicker", "Người dùng đã hủy chọn ảnh")
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finishAffinity()
            }
            .errorListener {
                Log.d("TedImagePicker", "Lỗi khi chọn ảnh!")
            }
            .start { uri ->
//                val intent = Intent(this, EditImageActivity::class.java).apply {
//                    putExtra(
//                        "URI_IMAGE_INPUT",
//                        uri
//                    )
//                }
//
//                startActivity(intent)
            }
    }

    override fun dataObservable() {
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
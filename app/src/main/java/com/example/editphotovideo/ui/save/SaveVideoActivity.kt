package com.example.editphotovideo.ui.save

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.editphotovideo.MyApplication
import com.example.editphotovideo.R
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.data.ImageData
import com.example.editphotovideo.databinding.ActivitySaveVideoBinding
import com.example.editphotovideo.service.CreateVideoService
import com.example.editphotovideo.service.ImageCreatorService
import com.example.editphotovideo.ui.editmovie.ImageEditActivity
import com.example.editphotovideo.ui.editorimage.EditImageActivity
import com.example.editphotovideo.ui.main.MainActivity
import com.example.editphotovideo.ui.tools.compressvideo.CompressVideoActivity
import com.example.editphotovideo.ui.tools.extract_audio.ExtractAudioActivity
import com.example.editphotovideo.ui.tools.reversevideo.ReverseVideoActivity
import com.example.editphotovideo.ui.tools.speed.SpeedActivity
import com.example.editphotovideo.ui.tools.trim.TrimActivity
import com.example.editphotovideo.utils.ImageUtils.getRealPathFromUri
import com.example.editphotovideo.utils.ShareUtils
import com.example.editphotovideo.utils.ShareUtils.shareVideo
import com.example.editphotovideo.utils.ViewUtils.createSeekBarChangeListener
import com.example.editphotovideo.utils.ViewUtils.formatTime
import com.example.editphotovideo.widget.showSnackBar
import com.example.editphotovideo.widget.tap
import com.example.editphotovideo.widget.visible
import gun0912.tedimagepicker.builder.TedImagePicker


class SaveVideoActivity : BaseActivity<ActivitySaveVideoBinding>() {
    private var videoUri: String? = null
    private var isPlaying = false
    private var isTracking = false
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var application: MyApplication
    var activity: KeyNewProject? = null


    override fun setViewBinding(): ActivitySaveVideoBinding {
        return ActivitySaveVideoBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val activityName = intent.getStringExtra("KEY_ACTIVITY")
        activity = KeyNewProject.valueOf(activityName ?:KeyNewProject.EDIT_VIDEO_ACTIVITY.name)
        videoUri = intent.getStringExtra("URI_VIDEO_INPUT")
        application = MyApplication.getInstance()
        if (videoUri != null) {
            Log.d("URI_VIDEO_INPUT", videoUri!!)
            setupVideoView(videoUri!!)
        }
        binding.seekBar.setOnSeekBarChangeListener(
            createSeekBarChangeListener(
                videoView = binding.videoView,
                onStart = { isTracking = true },
                onStop = { isTracking = false }
            )
        )

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
            finishAffinity()
        }
        binding.imgFaceBook.tap {
            shareVideo(this, ShareUtils.KeyShare.FACEBOOK, Uri.parse(videoUri))
        }
        binding.imgShareMore.tap {
            shareVideo(this, ShareUtils.KeyShare.SHARE_MORE, Uri.parse(videoUri))
        }
        binding.imgInstagram.tap {
            shareVideo(this, ShareUtils.KeyShare.INSTAGRAM, Uri.parse(videoUri))
        }
        binding.imgTiktok.tap {
            shareVideo(this, ShareUtils.KeyShare.TIKTOK, Uri.parse(videoUri))
        }
        binding.imgYoutube.tap {
            shareVideo(this, ShareUtils.KeyShare.YOUTUBE, Uri.parse(videoUri))
        }
        binding.imgMessenger.tap {
            shareVideo(this, ShareUtils.KeyShare.MESSENGER, Uri.parse(videoUri))
        }
        binding.imgWhatsapp.tap {
            shareVideo(this, ShareUtils.KeyShare.WHATSAPP, Uri.parse(videoUri))
        }
        binding.tvNewProject.tap {
            if (activity==KeyNewProject.EDIT_VIDEO_ACTIVITY){
                TedImagePicker.with(this)

                    .cancelListener {
                        Log.d("TedImagePicker", "Người dùng đã hủy chọn ảnh")
                    }
                    .errorListener {
                        Log.d("TedImagePicker", "Lỗi khi chọn ảnh!")
                    }
                    .min(3, getString(R.string.Please_select_at_least_3_photos))
                    .startMultiImage { uriList ->
                        application.clearAllSelection()
                        uriList.forEach { uri ->
                            val imagePath = getRealPathFromUri(this, uri)
                            val imageData = ImageData().apply {
                                this.imagePath = imagePath
                                this.folderName = "FromPicker"
                                this.imageCount = 1
                            }
                            application.addSelectedImage(imageData)
                        }
                        if (!isVideoInprocess()) {
                            val intent = Intent(this, ImageEditActivity::class.java)
                            intent.putParcelableArrayListExtra("selectedImages", ArrayList(uriList))
                            startActivity(intent)
                            finishAffinity()
                        }
                    }
            }else{
                selectVideoEdit()
            }
        }
    }
    private fun isVideoInprocess(): Boolean {
        return MyApplication.isMyServiceRunning(
            this,
            CreateVideoService::class.java
        ) || MyApplication.isMyServiceRunning(
            this,
            ImageCreatorService::class.java
        )
    }

    private fun getRealPathFromUri(context: Context, uri: Uri): String {
        var path = ""
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            path = it.getString(columnIndex)
        }
        return path
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

    private fun selectVideoEdit() {
        TedImagePicker.with(this)
            .video()
            .cancelListener {
                Log.d("TedImagePicker", "Người dùng đã hủy chọn ảnh")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
            .errorListener {
                Log.d("TedImagePicker", "Lỗi khi chọn ảnh!")
            }
            .start { uri ->
                val startActivity = when (activity) {
                    KeyNewProject.COMPRESS_ACTIVITY -> CompressVideoActivity::class.java
                    KeyNewProject.EXTRACT_ACTIVITY -> ExtractAudioActivity::class.java
                    KeyNewProject.REVERSE_ACTIVITY -> ReverseVideoActivity::class.java
                    KeyNewProject.SPEED_ACTIVITY -> SpeedActivity::class.java
                    KeyNewProject.TRIM_ACTIVITY -> TrimActivity::class.java
                    else -> {
                        MainActivity::class.java
                    }
                }
                val intent = Intent(this, startActivity).apply {
                    putExtra(
                        "URI_VIDEO_INPUT",
                        uri.toString()
                    )
                }

                startActivity(intent)
                finishAffinity()
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
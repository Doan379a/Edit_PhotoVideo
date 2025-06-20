package com.example.editphotovideo.ui.tools.trim

import android.media.MediaMetadataRetriever
import android.media.MediaMetadataRetriever.METADATA_KEY_DURATION
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import com.amp.trimmy.interfaces.VideoTrimmingListener
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.databinding.ActivityTrimBinding
import com.example.editphotovideo.utils.ImageUtils.getTempMovieDir
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class TrimActivity : BaseActivity<ActivityTrimBinding>(), VideoTrimmingListener {

    private var dstTrimmedFile: File? = null
    private var inputUri: Uri? = null

    private val timeFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
    private var durationSec: Long = 0

    override fun setViewBinding() = ActivityTrimBinding.inflate(layoutInflater)

    override fun initView() {
        intent.getStringExtra("URI_VIDEO_INPUT")?.let {
            inputUri = Uri.parse(it)
        } ?: run {
            Toast.makeText(this, "Không có video", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        dstTrimmedFile = File(getTempMovieDir(), "trimmed_${System.currentTimeMillis()}.mp4")
        val retriever = MediaMetadataRetriever().apply {
            setDataSource(this@TrimActivity, inputUri)
        }
        durationSec = retriever.extractMetadata(METADATA_KEY_DURATION)?.toLong()?: 0L
        binding.videoTrimmerView.apply {
            setDestinationFile(dstTrimmedFile!!)
            setOnK4LVideoListener(this@TrimActivity)
            setMinDurationInMs(1_000)     // tối thiểu 1 giây
            setMaxDurationInMs(durationSec.toInt())    // tối đa 15 giây (tuỳ chỉnh)
            post { setVideoURI(inputUri!!) }
        }

        binding.btnStart.setOnClickListener {
            binding.videoTrimmerView.initiateTrimming()
        }
    }

    override fun viewListener() { /* nếu có thêm UI khác */ }

    override fun dataObservable() { /* nếu dùng ViewModel/DataBinding */ }

    // Video loaded, nút trim giờ đã sẵn sàng
    override fun onVideoPrepared() {
        binding.progressBar.post {
            Toast.makeText(this, "Video sẵn sàng", Toast.LENGTH_SHORT).show()
            updateRangeText()
        }
    }

    override fun onTrimStarted() {
        binding.videoTrimmerView.post {
            binding.progressBar.visibility = View.VISIBLE
            Toast.makeText(this, "Bắt đầu cắt...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onTrimProgressing(progress: Double) {
        // progress từ 0.0 đến 1.0
        binding.progressBar.post {
            binding.progressBar.visibility = View.VISIBLE
            Log.d("TrimActivity", "Progress: ${progress * 100}%")
            updateRangeText()
        }
    }

    override fun onFinishedTrimming(uri: Uri?) {
        binding.progressBar.post {
            binding.progressBar.visibility = View.GONE
            Toast.makeText(
                this,

                "Trim xong: ${uri?.path ?: dstTrimmedFile?.absolutePath}",
                Toast.LENGTH_LONG
            ).show()
            Log.d("TrimActivity", "Destination file path: ${dstTrimmedFile?.absolutePath}")

            // Có thể trả lại data hoặc dùng đoạn file tiếp tục xử lý
        }
    }

    override fun onTrimFailed(exception: Exception?) {
        binding.progressBar.post {
            binding.progressBar.visibility = View.GONE
            Toast.makeText(
                this,
                "Trim thất bại: ${exception?.message ?: "Không xác định"}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onErrorWhileViewingVideo(what: Int, extra: Int) {
        Toast.makeText(this, "Lỗi khi xem video: what=$what, extra=$extra", Toast.LENGTH_LONG).show()
    }

    private fun updateRangeText() {
        val startMs = binding.videoTrimmerView.left
        val endMs = binding.videoTrimmerView.right
        binding.tvRange.text = "${timeFormat.format(Date(startMs.toLong()))} - " +
                "${timeFormat.format(Date(endMs.toLong()))}"
    }
}

package com.example.editphotovideo.ui.tools.trim

import android.content.Intent
import android.media.MediaMetadataRetriever
import android.media.MediaMetadataRetriever.METADATA_KEY_DURATION
import android.media.MediaScannerConnection
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.amp.trimmy.interfaces.VideoTrimmingListener
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.data.entity.MediaEntity
import com.example.editphotovideo.data.entity.MediaType
import com.example.editphotovideo.data.viewmodel.MediaViewModel
import com.example.editphotovideo.databinding.ActivityTrimBinding
import com.example.editphotovideo.ui.save.SaveVideoActivity
import com.example.editphotovideo.utils.ImageUtils.getTempMovieDir
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
@AndroidEntryPoint
class TrimActivity : BaseActivity<ActivityTrimBinding>(), VideoTrimmingListener {

    private var dstTrimmedFile: File? = null
    private var inputUri: Uri? = null

    private val timeFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
    private var durationSec: Long = 0
    private  val mediaViewModel: MediaViewModel by viewModels()

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

        binding.tvSave.setOnClickListener {
            binding.videoTrimmerView.initiateTrimming()
        }
    }

    override fun viewListener() { }

    override fun dataObservable() { }

    override fun onVideoPrepared() {
        binding.progressBar.post {
            Toast.makeText(this, "Video sẵn sàng", Toast.LENGTH_SHORT).show()
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
        }
    }

    override fun onFinishedTrimming(uri: Uri?) {
        binding.progressBar.post {
            binding.progressBar.visibility = View.GONE
            MediaScannerConnection.scanFile(
                this@TrimActivity,
                arrayOf(uri?.path),
                arrayOf("video/mp4"),
                null
            )
            val entity = MediaEntity(
                filePath = uri.toString(),
                mediaType = MediaType.VIDEO
            )
            mediaViewModel.insertMedia(entity)
            val intent = Intent(this@TrimActivity, SaveVideoActivity::class.java)
            intent.putExtra("URI_VIDEO_INPUT", uri.toString())
            startActivity(intent)
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

}

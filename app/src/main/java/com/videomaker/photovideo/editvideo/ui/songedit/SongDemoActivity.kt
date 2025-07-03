package com.videomaker.photovideo.editvideo.ui.songedit

import android.app.ProgressDialog
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.videomaker.photovideo.editvideo.MyApplication
import com.videomaker.photovideo.editvideo.R
import com.videomaker.photovideo.editvideo.base.BaseActivity
import com.videomaker.photovideo.editvideo.data.MusicData
import com.videomaker.photovideo.editvideo.data.SongData
import com.videomaker.photovideo.editvideo.databinding.ActivityAddMusicBinding
import com.videomaker.photovideo.editvideo.libffmpeg.FileUtils
import com.videomaker.photovideo.editvideo.widget.tap
import java.io.File
import java.io.FileOutputStream

class SongDemoActivity : BaseActivity<ActivityAddMusicBinding>() {
    private var mediaPlayer = MediaPlayer()
    private lateinit var mAdapter: SongAdapter
    private lateinit var selectedMusicData :SongData
    private lateinit var myApplication: MyApplication
    override fun setViewBinding(): ActivityAddMusicBinding {
        return  ActivityAddMusicBinding.inflate(layoutInflater)
    }

    override fun initView() {
        myApplication = MyApplication.getInstance()
        binding.toolbarTitle.text = getString(R.string.demo_music)
        val list = listOf(
            SongData(0, "Song 1", R.raw._1,0L),
            SongData(1, "Song 2", R.raw._2,0L),
            SongData(2, "Song 3", R.raw._3,0L),
            SongData(3, "Song 4", R.raw._4,0L),
            SongData(4, "Song 5", R.raw._5,0L),
        )
        mAdapter = SongAdapter(this){data->
            selectedMusicData= data
            mediaPlayer.reset()
            val uri = Uri.parse("android.resource://${packageName}/${data.track_displayName}")

            mediaPlayer.setDataSource(this, uri)
            mediaPlayer.prepare()
            mediaPlayer.start()
        }
        binding.rvMusicList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvMusicList.adapter = mAdapter
        mAdapter.updateList(list)
    }

    override fun viewListener() {
        binding.ivBack.tap {
             finish()
        }
        binding.ivDonePreview.tap {
            if (selectedMusicData == null) {
                Toast.makeText(this, "No music selected", Toast.LENGTH_SHORT).show()
                return@tap
            }

            val dialog = ProgressDialog(this).apply {
                setTitle(R.string.progress_dialog_saving)
                isIndeterminate = true
                setCancelable(false)
                show()
            }

            FileUtils.TEMP_DIRECTORY_AUDIO.mkdirs()
            val audioDir = File(getExternalFilesDir(null), "Photo_Video_Edit/.temp_audio").apply { mkdirs() }
            val tempFile = File(audioDir, "temp.mp3")
            if (tempFile.exists()) {
                FileUtils.deleteFile(tempFile)
            }

            try {
                val inputStream = resources.openRawResource(selectedMusicData.track_displayName)
                val outputStream = FileOutputStream(tempFile)
                inputStream.use { input ->
                    outputStream.use { output ->
                        val buffer = ByteArray(1024)
                        var bytesRead: Int
                        while (input.read(buffer).also { bytesRead = it } > 0) {
                            output.write(buffer, 0, bytesRead)
                        }
                    }
                }

                val mediaPlayer = MediaPlayer().apply {
                    setDataSource(tempFile.absolutePath)
                    setAudioStreamType(AudioManager.STREAM_MUSIC)
                    prepare()
                }

                val musicData = MusicData().apply {
                    track_data = tempFile.absolutePath
                    track_Title = selectedMusicData.track_Title
                }

                mediaPlayer.setOnPreparedListener { mp ->
                    musicData.track_duration = mp.duration.toLong()
                    MyApplication.getInstance().setMusicData(musicData)

                    dialog.dismiss()
                    setResult(RESULT_OK)
                    finish()
                }

            } catch (e: Exception) {
                dialog.dismiss()
                e.printStackTrace()
                Toast.makeText(this, "Failed to load music", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun dataObservable() {

    }
    override fun onBackPressed() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            mediaPlayer.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun onPause() {
        super.onPause()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }


}
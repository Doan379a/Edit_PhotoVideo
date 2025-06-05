package com.example.editphotovideo.ui.removebackgr

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.databinding.ActivityRemoveBackGrBinding
import com.example.editphotovideo.library.removebackgr.RemoveBg
import com.example.editphotovideo.ui.main.MainActivity
import com.example.editphotovideo.utils.ImageUtils.getCorrectlyOrientedBitmap
import com.example.editphotovideo.utils.ImageUtils.setUpZoomSettings
import com.example.editphotovideo.utils.getBitmapFromAsset
import com.example.editphotovideo.widget.gone
import com.example.editphotovideo.widget.visible
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class RemoveBackGrActivity : BaseActivity<ActivityRemoveBackGrBinding>() {
    private var outputImage: Bitmap? = null
    private var imgUri: Uri? = null
    private val removeBg by lazy { RemoveBg(context = this) }
    private lateinit var adapter: BackGroundAdapter

    override fun setViewBinding(): ActivityRemoveBackGrBinding {
        return ActivityRemoveBackGrBinding.inflate(layoutInflater)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri = result.data?.getParcelableExtra<Uri>("cropped_image_uri")
                binding.imageView.setImageURI(uri)
            }
        }

    override fun initView() {
        setUpZoomSettings(binding.imageView)
        setUpRemoveBg()
        setUpRecyclerView()
    }

    override fun viewListener() {
        binding.btnRemovePain.setOnClickListener {
            val intent = Intent(this, FreeHandCropperActivity::class.java)
            intent.putExtra("image_uri", imgUri)
            launcher.launch(intent)
        }
    }

    override fun dataObservable() {
    }

    private fun setUpRecyclerView() = binding.apply {
        val layoutManager =
            LinearLayoutManager(this@RemoveBackGrActivity, LinearLayoutManager.HORIZONTAL, false)
        rcvBackground.layoutManager = layoutManager
        adapter = BackGroundAdapter(
            context = this@RemoveBackGrActivity,
            list = BackGroundAdapter.backgrList
        ) { imgString, position ->
            if (position == 0) {
                selectImageBackgr()
            } else {
                val fromAsset = getBitmapFromAsset(this@RemoveBackGrActivity, imgString)
                Glide.with(this@RemoveBackGrActivity)
                    .load(fromAsset)
                    .into(imgBackground)
            }

        }
        rcvBackground.adapter = adapter
        rcvBackground.setHasFixedSize(true)
    }

    private fun setUpRemoveBg() = binding.apply {
        val imageUri: Uri? = intent.getParcelableExtra("URI_IMAGE_INPUT")
        if (imageUri != null) {
            imgUri = imageUri
            val bitmap = getCorrectlyOrientedBitmap(this@RemoveBackGrActivity, imageUri)
            processImage(bitmap)
        } else {
            Log.e("RemoveBackGrActivity", "No image URI provided")
        }
    }

    private fun processImage(bitmap: Bitmap) {
        CoroutineScope(Dispatchers.Main).launch {
            removeBg.clearBackground(bitmap)
                .onStart {
                    binding.progressBar.visible()
                    Log.d("RemoveBg", "Start removing background")
                }
                .onCompletion {
                    binding.progressBar.gone()
                    Log.d("RemoveBg", "Finished processing")
                }
                .catch { e ->
                    e.printStackTrace()
                    Log.d("RemoveBg", "Finished $e")
                    binding.progressBar.gone()
                }
                .collect { output ->
                    outputImage = output
                    binding.imageView.setImageBitmap(outputImage)
                }
        }
    }

    private fun selectImageBackgr() = binding.apply {
        TedImagePicker.with(this@RemoveBackGrActivity)
            .cancelListener {
                Log.d("TedImagePicker", "Người dùng đã hủy chọn ảnh")
            }
            .errorListener {
                Log.d("TedImagePicker", "Lỗi khi chọn ảnh!")
            }
            .start { uri ->
                Glide.with(this@RemoveBackGrActivity)
                    .load(uri)
                    .into(imgBackground)
            }
    }

}
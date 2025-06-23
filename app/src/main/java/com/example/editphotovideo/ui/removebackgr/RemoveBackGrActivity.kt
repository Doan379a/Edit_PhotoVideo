package com.example.editphotovideo.ui.removebackgr

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.ahmadhamwi.tabsync.TabbedListMediator
import com.bumptech.glide.Glide
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.data.DSDatabase
import com.example.editphotovideo.data.entity.MediaEntity
import com.example.editphotovideo.data.entity.MediaType
import com.example.editphotovideo.data.repository.MediaRepository
import com.example.editphotovideo.data.viewmodel.MediaViewModel
import com.example.editphotovideo.data.viewmodel.MediaViewModelFactory
import com.example.editphotovideo.databinding.ActivityRemoveBackGrBinding
import com.example.editphotovideo.library.removebackgr.RemoveBg
import com.example.editphotovideo.ui.main.template.model.TemplateType
import com.example.editphotovideo.ui.main.template.model.getAllSection
import com.example.editphotovideo.ui.main.template.model.getAllTemplate
import com.example.editphotovideo.ui.save.SaveImageActivity
import com.example.editphotovideo.utils.ImageUtils.DEFAULT_FOLDER
import com.example.editphotovideo.utils.ImageUtils.getCorrectlyOrientedBitmap
import com.example.editphotovideo.utils.ImageUtils.setUpZoomSettings
import com.example.editphotovideo.utils.getBitmapFromAsset
import com.example.editphotovideo.widget.getTagDebug
import com.example.editphotovideo.widget.gone
import com.example.editphotovideo.widget.tap
import com.example.editphotovideo.widget.visible
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.util.ToastUtil.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.io.IOException

@AndroidEntryPoint
class RemoveBackGrActivity : BaseActivity<ActivityRemoveBackGrBinding>() {
    private var outputImage: Bitmap? = null
    private var imgUri: Uri? = null
    private val removeBg by lazy { RemoveBg(context = this) }
    private lateinit var adapter: BackGroundAdapter
    private lateinit var mediator: TabbedListMediator
    private var templateId: String? = null
    private  val mediaViewModel:MediaViewModel by viewModels()

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
        Log.d(getTagDebug(), ",sizeeeee ${getAllTemplate().size},,,,kkkkkk ${getAllTemplate()}")
        templateId = intent.getStringExtra("templateId")
        Log.d(getTagDebug(), ",iddddddd22222 $templateId")
        setUpZoomSettings(binding.imageView)
        setUpRemoveBg()
        setUpRecyclerView()
    }

    override fun viewListener() {
//        binding.btnRemovePain.setOnClickListener {
//            val intent = Intent(this, FreeHandCropperActivity::class.java)
//            intent.putExtra("image_uri", imgUri)
//            launcher.launch(intent)
//        }
        binding.imgBack.tap {
            finish()
        }
        binding.imgDowload.tap {
            saveImageRemoveToFile(binding.frameImage)?.let { uri ->
                val intent = Intent(this,SaveImageActivity::class.java)
                val entity = MediaEntity(
                    filePath = uri,
                    mediaType = MediaType.IMAGE
                )
                mediaViewModel.insertMedia(entity)
                intent.putExtra("output_image_uri", uri)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun dataObservable() {
    }

    private fun setUpRecyclerView() = binding.apply {
        val sectionTypes = TemplateType.values()
        val listTemplates = getAllTemplate()
        getAllSection(this@RemoveBackGrActivity).forEach {
            tabLayout.addTab(tabLayout.newTab().setText(it.title.capitalize()))
        }
        val layoutManager =
            LinearLayoutManager(this@RemoveBackGrActivity, RecyclerView.HORIZONTAL, false)
        rcvBackground.layoutManager = layoutManager

        adapter = BackGroundAdapter(
            context = this@RemoveBackGrActivity,
            list = listTemplates
        ) { list, position ->
            val bitmap = if (position == 0) {
                selectImageBackgr()
                null
            } else getBitmapFromAsset(this@RemoveBackGrActivity, list.imgbackgr)

            bitmap?.let {
                Glide.with(this@RemoveBackGrActivity)
                    .load(it)
                    .into(imgBackground)
            }
        }

        rcvBackground.adapter = adapter
        val selectedTemplateId = templateId?.toIntOrNull()
        selectedTemplateId?.let { id ->
            val index = listTemplates.indexOfFirst { it.id == id }
            if (index != -1) {
                rcvBackground.scrollToPosition(index)
                val bitmap =
                    getBitmapFromAsset(this@RemoveBackGrActivity, listTemplates[index].imgbackgr)
                bitmap?.let {
                    Glide.with(this@RemoveBackGrActivity)
                        .load(it)
                        .into(imgBackground)
                }
                val section = listTemplates[index].sectionType
                val tabIndex = TemplateType.values().indexOf(section)
                if (tabIndex != -1) {
                    binding.tabLayout.getTabAt(tabIndex)?.select()
                }
            }
            adapter.setSelectedId(id)
        }

        val indices = sectionTypes.map { type ->
            listTemplates.indexOfFirst { it.sectionType == type }
        }
        mediator = TabbedListMediator(binding.rcvBackground, binding.tabLayout, indices, false)
        mediator.attach()

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

    private fun saveImageRemoveToFile(frameLayout: FrameLayout): String? {

        val bitmap =
            Bitmap.createBitmap(frameLayout.width, frameLayout.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        frameLayout.draw(canvas)

        val fileName = "Remove_background_${System.currentTimeMillis()}.jpg"

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                "DCIM/$DEFAULT_FOLDER"
            )
        }

        val resolver = contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let {
            try {
                resolver.openOutputStream(it)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }
//                showToast("Saved to HD_camera")
                return it.toString()
            } catch (e: IOException) {
                e.printStackTrace()
                Log.d("DEBUG", "Error saving image: ${e.message}")
            }
        }

        return null
    }
}
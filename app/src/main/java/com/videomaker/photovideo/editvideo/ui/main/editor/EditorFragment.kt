package com.videomaker.photovideo.editvideo.ui.main.editor

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.videomaker.photovideo.editvideo.MyApplication
import com.videomaker.photovideo.editvideo.R
import com.videomaker.photovideo.editvideo.base.BaseFragment
import com.videomaker.photovideo.editvideo.data.ImageData
import com.videomaker.photovideo.editvideo.databinding.FragmentEditorBinding
import com.videomaker.photovideo.editvideo.service.CreateVideoService
import com.videomaker.photovideo.editvideo.service.ImageCreatorService
import com.videomaker.photovideo.editvideo.ui.editmovie.ImageEditActivity
import com.videomaker.photovideo.editvideo.ui.editorimage.EditImageActivity
import com.videomaker.photovideo.editvideo.ui.removebackgr.RemoveBackGrActivity
import com.videomaker.photovideo.editvideo.ui.tools.ToolsActivity
import com.videomaker.photovideo.editvideo.widget.tap
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.TedImagePickerDialog

class EditorFragment : BaseFragment<FragmentEditorBinding>() {
    private lateinit var application: MyApplication
    override fun setViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditorBinding {
        return FragmentEditorBinding.inflate(layoutInflater)
    }

    override fun initView() {
        application = MyApplication.getInstance()
        binding.imgRemoveBackgr.setOnClickListener {
            removeBackGround()
        }
        binding.imgEnhanceBeauty.setOnClickListener {
            selectImageEdit()
        }
        binding.imgTools.tap {
            requireActivity().startActivity(Intent(requireActivity(), ToolsActivity::class.java))
        }
        binding.llCreateVideo.tap {
            TedImagePicker.with(requireActivity())

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
                        val imagePath = getRealPathFromUri(requireActivity(), uri)
                        val imageData = ImageData().apply {
                            this.imagePath = imagePath
                            this.folderName = "FromPicker"
                            this.imageCount = 1
                        }
                        application.addSelectedImage(imageData)
                    }
                    if (!isVideoInprocess()) {
                        val intent = Intent(requireActivity(), ImageEditActivity::class.java)
                        intent.putParcelableArrayListExtra("selectedImages", ArrayList(uriList))
                        startActivity(intent)
                    }
                }
        }
    }

    override fun viewListener() {
    }

    override fun dataObservable() {
    }

    private fun selectImageEdit() {
        TedImagePicker.with(requireActivity())
            .cancelListener {
                Log.d("TedImagePicker", "Người dùng đã hủy chọn ảnh")
            }
            .errorListener {
                Log.d("TedImagePicker", "Lỗi khi chọn ảnh!")
            }
            .start { uri ->
                val intent = Intent(requireActivity(), EditImageActivity::class.java).apply {
                    putExtra(
                        "URI_IMAGE_INPUT",
                        uri
                    )
                }

                startActivity(intent)
            }
    }

    private fun removeBackGround() {
        TedImagePickerDialog.with(requireActivity())
            .cancelListener{
                Log.d("TedImagePicker", "Người dùng đã hủy chọn ảnh")
            }
            .errorListener {
                Log.d("TedImagePicker", "Lỗi khi chọn ảnh!")
            }
            .start { uri ->
                val intent = Intent(requireActivity(), RemoveBackGrActivity::class.java).apply {
                    putExtra(
                        "URI_IMAGE_INPUT",
                        uri
                    )
                }
                startActivity(intent)
            }
    }

    private fun isVideoInprocess(): Boolean {
        return MyApplication.isMyServiceRunning(
            requireActivity(),
            CreateVideoService::class.java
        ) || MyApplication.isMyServiceRunning(
            requireActivity(),
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
}
package com.example.editphotovideo.ui.main.editor

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.editphotovideo.MyApplication
import com.example.editphotovideo.base.BaseFragment
import com.example.editphotovideo.data.ImageData
import com.example.editphotovideo.databinding.FragmentEditorBinding
import com.example.editphotovideo.service.CreateVideoService
import com.example.editphotovideo.service.ImageCreatorService
import com.example.editphotovideo.ui.editmovie.ImageEditActivity
import com.example.editphotovideo.ui.editorimage.EditImageActivity
import com.example.editphotovideo.ui.removebackgr.RemoveBackGrActivity
import com.example.editphotovideo.ui.tools.ToolsActivity
import com.example.editphotovideo.widget.tap
import gun0912.tedimagepicker.builder.TedImagePicker

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
        TedImagePicker.with(requireActivity())
            .cancelListener {
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
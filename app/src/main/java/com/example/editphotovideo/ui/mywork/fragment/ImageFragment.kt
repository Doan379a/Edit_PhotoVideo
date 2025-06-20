package com.example.editphotovideo.ui.mywork.fragment

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.editphotovideo.base.BaseFragment
import com.example.editphotovideo.data.entity.MediaType
import com.example.editphotovideo.data.viewmodel.MediaViewModel
import com.example.editphotovideo.databinding.FragmentImageBinding
import com.example.editphotovideo.ui.editorimage.EditImageActivity
import com.example.editphotovideo.ui.main.MainActivity
import com.example.editphotovideo.ui.save.SaveImageActivity
import com.example.editphotovideo.widget.getTagDebug
import com.example.editphotovideo.widget.tap
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker

@AndroidEntryPoint
class ImageFragment : BaseFragment<FragmentImageBinding>() {
    private val mediaViewModel: MediaViewModel by activityViewModels()
    private lateinit var adapter: MediaAdapter

    override fun setViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentImageBinding {
        return FragmentImageBinding.inflate(layoutInflater)
    }

    override fun initView() {
        adapter = MediaAdapter(mutableListOf()) { path ->
            Log.d(getTagDebug(), "path: $path")
            val intent = Intent(requireActivity(), SaveImageActivity::class.java)
            intent.putExtra("output_image_uri", path)
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.recyclerView.adapter = adapter
        mediaViewModel.mediaList.observe(requireActivity()) { mediaList ->
            val imageMedia =
                mediaList.filter { it.mediaType == MediaType.IMAGE }.map { it.filePath }
            Log.d(getTagDebug(), "image list: $imageMedia")
            adapter.updateData(imageMedia)
        }

    }

    override fun viewListener() {

    }


    override fun dataObservable() {
    }
}
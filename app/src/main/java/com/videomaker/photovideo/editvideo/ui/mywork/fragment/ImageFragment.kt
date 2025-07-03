package com.videomaker.photovideo.editvideo.ui.mywork.fragment

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.videomaker.photovideo.editvideo.base.BaseFragment
import com.videomaker.photovideo.editvideo.data.entity.MediaType
import com.videomaker.photovideo.editvideo.data.viewmodel.MediaViewModel
import com.videomaker.photovideo.editvideo.databinding.FragmentImageBinding
import com.videomaker.photovideo.editvideo.ui.editorimage.EditImageActivity
import com.videomaker.photovideo.editvideo.ui.main.MainActivity
import com.videomaker.photovideo.editvideo.ui.save.SaveImageActivity
import com.videomaker.photovideo.editvideo.widget.getTagDebug
import com.videomaker.photovideo.editvideo.widget.gone
import com.videomaker.photovideo.editvideo.widget.tap
import com.videomaker.photovideo.editvideo.widget.visible
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
            if (imageMedia.isEmpty()) {
                binding.imgNodata.visible()
            } else {
                binding.imgNodata.gone()
                Log.d(getTagDebug(), "image list: $imageMedia")
                adapter.updateData(imageMedia)
            }

        }

    }

    override fun viewListener() {

    }


    override fun dataObservable() {
    }
}
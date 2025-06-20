package com.example.editphotovideo.ui.mywork.fragment

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.editphotovideo.base.BaseFragment
import com.example.editphotovideo.data.entity.MediaType
import com.example.editphotovideo.data.viewmodel.MediaViewModel
import com.example.editphotovideo.databinding.FragmentVideoBinding
import com.example.editphotovideo.widget.getTagDebug
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoFragment : BaseFragment<FragmentVideoBinding>() {
    private val mediaViewModel: MediaViewModel by activityViewModels()
    private lateinit var adapter: MediaAdapter

    override fun setViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentVideoBinding {
        return FragmentVideoBinding.inflate(layoutInflater)
    }

    override fun initView() {
        adapter = MediaAdapter(mutableListOf()) {path->
            Log.d(getTagDebug(), "path: $path")
        }
        binding.recyclerView.layoutManager = GridLayoutManager(requireActivity(),2)
        binding.recyclerView.adapter = adapter
        mediaViewModel.mediaList.observe(requireActivity()) { mediaList ->
            val videoMedia =
                mediaList.filter { it.mediaType == MediaType.VIDEO }.map { it.filePath }
            Log.d(getTagDebug(), "videolist: $videoMedia")
            adapter.updateData(videoMedia)
        }
    }

    override fun viewListener() {
    }

    override fun dataObservable() {
    }
}
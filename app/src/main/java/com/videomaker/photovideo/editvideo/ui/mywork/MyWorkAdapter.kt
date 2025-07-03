package com.videomaker.photovideo.editvideo.ui.mywork

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.videomaker.photovideo.editvideo.ui.mywork.fragment.ImageFragment
import com.videomaker.photovideo.editvideo.ui.mywork.fragment.VideoFragment

class MyWorkAdapter(fragmentManager: FragmentActivity) : FragmentStateAdapter(fragmentManager) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> VideoFragment()
            1 -> ImageFragment()
            else -> VideoFragment()
        }
    }
}
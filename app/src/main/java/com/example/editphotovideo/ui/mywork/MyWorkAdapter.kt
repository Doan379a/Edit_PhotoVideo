package com.example.editphotovideo.ui.mywork

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.editphotovideo.ui.mywork.fragment.ImageFragment
import com.example.editphotovideo.ui.mywork.fragment.VideoFragment

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
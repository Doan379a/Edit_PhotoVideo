package com.example.editphotovideo.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.editphotovideo.ui.main.editor.EditorFragment
import com.example.editphotovideo.ui.main.template.TemplateFragment

class MainAdapter(fragmentManager : FragmentActivity) : FragmentStateAdapter(fragmentManager) {
    override fun getItemCount(): Int  = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> EditorFragment()
            1 -> TemplateFragment()
            else -> EditorFragment()
        }
    }
}
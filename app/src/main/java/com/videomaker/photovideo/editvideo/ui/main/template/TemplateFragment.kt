package com.videomaker.photovideo.editvideo.ui.main.template

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.videomaker.photovideo.editvideo.base.BaseFragment
import com.videomaker.photovideo.editvideo.databinding.FragmentTemplateBinding
import com.videomaker.photovideo.editvideo.ui.main.template.adapter.SectionAdapter
import com.videomaker.photovideo.editvideo.ui.main.template.model.getAllSection
import com.videomaker.photovideo.editvideo.ui.main.template.model.getSectionAutumn
import com.videomaker.photovideo.editvideo.ui.main.template.model.getSectionHaloween
import com.videomaker.photovideo.editvideo.ui.main.template.model.getSectionNeon
import com.videomaker.photovideo.editvideo.ui.main.template.model.getSectionNoel
import com.videomaker.photovideo.editvideo.ui.main.template.model.getSectionTrending
import com.videomaker.photovideo.editvideo.ui.main.template.model.getSectionWedding
import com.videomaker.photovideo.editvideo.ui.main.template.select.SelectTemplateActivity

class TemplateFragment : BaseFragment<FragmentTemplateBinding>() {
    override fun setViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTemplateBinding {
        return FragmentTemplateBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        val listSection = getAllSection(requireActivity())

        binding.rvSections.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvSections.adapter =
            SectionAdapter(requireActivity(), listSection) { type, section, template ->
                Log.d("TemplateFragment", "Section: $section, Template: $template")
                val intent = Intent(requireActivity(), SelectTemplateActivity::class.java)
                intent.putExtra("sectionTitle", section)
                intent.putExtra("type", type)
                intent.putExtra("templateId", template.id.toString())
                startActivity(intent)
            }
    }

    override fun viewListener() {
    }

    override fun dataObservable() {
    }
}
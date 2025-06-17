package com.example.editphotovideo.ui.main.template

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.editphotovideo.base.BaseFragment
import com.example.editphotovideo.databinding.FragmentTemplateBinding
import com.example.editphotovideo.ui.main.template.adapter.SectionAdapter
import com.example.editphotovideo.ui.main.template.model.getAllSection
import com.example.editphotovideo.ui.main.template.model.getSectionAutumn
import com.example.editphotovideo.ui.main.template.model.getSectionHaloween
import com.example.editphotovideo.ui.main.template.model.getSectionNeon
import com.example.editphotovideo.ui.main.template.model.getSectionNoel
import com.example.editphotovideo.ui.main.template.model.getSectionTrending
import com.example.editphotovideo.ui.main.template.model.getSectionWedding
import com.example.editphotovideo.ui.main.template.select.SelectTemplateActivity

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
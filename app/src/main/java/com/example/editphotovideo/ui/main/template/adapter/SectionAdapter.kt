package com.example.editphotovideo.ui.main.template.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.editphotovideo.databinding.ItemSectionBinding
import com.example.editphotovideo.ui.main.template.model.Template
import com.example.editphotovideo.ui.main.template.model.TemplateSection
import com.example.editphotovideo.ui.main.template.model.TemplateType

class SectionAdapter(
    private val context: Context,
    private val sections: List<TemplateSection>,
    private val onTemplateClick: (TemplateType, String, Template) -> Unit
) : RecyclerView.Adapter<SectionAdapter.SectionViewHolder>() {

    inner class SectionViewHolder(val binding: ItemSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val templateAdapter = TemplateAdapter(context) { type, title, template ->
            onTemplateClick(type, title, template)
        }

        init {
            binding.rvTemplates.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = templateAdapter
                setHasFixedSize(true)
            }
        }

        fun bind(section: TemplateSection) {
            binding.tvSectionTitle.text = section.title
            templateAdapter.setClickMeta(section.type, section.title)
            templateAdapter.submitList(section.templates)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val binding = ItemSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        holder.bind(sections[position])
    }

    override fun getItemCount() = sections.size
}

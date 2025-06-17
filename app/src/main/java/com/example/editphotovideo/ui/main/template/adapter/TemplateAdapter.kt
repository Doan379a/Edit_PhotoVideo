package com.example.editphotovideo.ui.main.template.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.editphotovideo.databinding.ItemTemplateBinding
import com.example.editphotovideo.ui.main.template.model.Template
import com.example.editphotovideo.ui.main.template.model.TemplateType
import com.example.editphotovideo.utils.getBitmapFromAsset
import kotlin.text.Typography.section

class TemplateAdapter(
    private val context: Context,
    private val onClick: (TemplateType,String,Template) -> Unit
) : RecyclerView.Adapter<TemplateAdapter.TemplateViewHolder>() {
    private var currentType: TemplateType? = null
    private var currentTitle: String = ""
    private val items = mutableListOf<Template>()

    fun setClickMeta(type: TemplateType, title: String) {
        currentType = type
        currentTitle = title
    }

    fun submitList(newItems: List<Template>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class TemplateViewHolder(val binding: ItemTemplateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(template: Template) {
            val bitmap = context.getBitmapFromAsset(context, template.imagePath)
            binding.imageView.setImageBitmap(bitmap)

            binding.root.setOnClickListener {
                currentType?.let { type ->
                    onClick(type, currentTitle, template)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateViewHolder {
        val binding = ItemTemplateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TemplateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TemplateViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}

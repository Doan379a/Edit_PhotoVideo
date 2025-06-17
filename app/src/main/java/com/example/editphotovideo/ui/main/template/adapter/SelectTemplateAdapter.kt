package com.example.editphotovideo.ui.main.template.adapter

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.editphotovideo.R
import com.example.editphotovideo.databinding.ItemRcvSelectTemplateBinding
import com.example.editphotovideo.ui.main.template.model.Template
import com.example.editphotovideo.utils.getBitmapFromAsset

class SelectTemplateAdapter (val context: Context, private val imageList: List<Template>) :
    RecyclerView.Adapter<SelectTemplateAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(private val binding: ItemRcvSelectTemplateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(template: Template) {
            val bitmap = context.getBitmapFromAsset(context, template.imagePath)
            Glide.with(binding.imageView.context)
                .load(bitmap)
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(R.drawable.img_loadding)
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemRcvSelectTemplateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int = imageList.size

    fun getTemplateAt(position: Int): Template? {
        return if (position in imageList.indices) imageList[position] else null
    }


}

fun RecyclerView.getSnapPosition(snapHelper: LinearSnapHelper): Int {
    val layoutManager = this.layoutManager as? LinearLayoutManager ?: return RecyclerView.NO_POSITION
    val snapView = snapHelper.findSnapView(layoutManager)
    return snapView?.let { layoutManager.getPosition(it) } ?: RecyclerView.NO_POSITION
}

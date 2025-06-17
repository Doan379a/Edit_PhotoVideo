package com.example.editphotovideo.ui.removebackgr

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.editphotovideo.R
import com.example.editphotovideo.databinding.ItemBackgrImageBinding
import com.example.editphotovideo.ui.main.template.model.DisplayTemplate
import com.example.editphotovideo.utils.getBitmapFromAsset

class BackGroundAdapter(
    private val context: Context,
    val list: List<DisplayTemplate>,
    private val onItemClick: (DisplayTemplate, Int) -> Unit
) : RecyclerView.Adapter<BackGroundAdapter.ViewBackgroundHolder>() {

    private var selectedId: Int? = null

    inner class ViewBackgroundHolder(val binding: ItemBackgrImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DisplayTemplate, position: Int) {
            val isSelected = item.id == selectedId
            val fromAsset = context.getBitmapFromAsset(context, item.imagePath)

            if (position == 0) {
                if (isSelected) {
                    binding.imgFilterView.setImageResource(R.drawable.img_chosse_selected)
                } else {
                    binding.imgFilterView.setImageResource(R.drawable.img_chosse)
                }
            } else {
                Glide.with(context)
                    .asBitmap()
                    .load(fromAsset)
                    .placeholder(R.drawable.img_loadding)
                    .into(binding.imgFilterView)
            }

            binding.cardBoder.setCardBackgroundColor(
                if (isSelected) context.getColor(R.color.color_selector_tab) else Color.parseColor("#939393")
            )

            binding.root.setOnClickListener {
                if (selectedId != item.id) {
                    val oldIndex = list.indexOfFirst { it.id == selectedId }
                    selectedId = item.id
                    notifyItemChanged(oldIndex)
                    notifyItemChanged(position)
                    onItemClick(item, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBackgroundHolder {
        val binding = ItemBackgrImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewBackgroundHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewBackgroundHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    fun setSelectedId(id: Int) {
        val oldIndex = list.indexOfFirst { it.id == selectedId }
        val newIndex = list.indexOfFirst { it.id == id }
        selectedId = id
        notifyItemChanged(oldIndex)
        notifyItemChanged(newIndex)
    }
}

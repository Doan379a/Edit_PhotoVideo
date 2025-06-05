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
import com.example.editphotovideo.utils.getBitmapFromAsset

class BackGroundAdapter(
    private val context: Context,
    val list: List<String>,
    private val onItemClick: (String, Int) -> Unit
) :
    RecyclerView.Adapter<BackGroundAdapter.ViewBackgroundHolder>() {
    inner class ViewBackgroundHolder(val binding: ItemBackgrImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var selectedPosition: Int = -1


        fun bind(imageUrl: String, position: Int) {
            val fromAsset = context.getBitmapFromAsset(context, imageUrl)
            if (position == 0) {
                binding.imgFilterView.setImageResource(R.drawable.img_chosse)
            } else {
                Glide.with(context)
                    .asBitmap()
                    .load(fromAsset)
                    .placeholder(R.drawable.img_loadding)
                    .into(binding.imgFilterView)
            }
            binding.cardBoder.setCardBackgroundColor(
                if (selectedPosition == position) context.getColor(R.color.purple_500) else Color.WHITE
            )
            binding.imgFilterView.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
                onItemClick(imageUrl, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBackgroundHolder {
        val binding = ItemBackgrImageBinding.inflate(
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
            parent,
            false
        )
        return ViewBackgroundHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewBackgroundHolder, position: Int) {
        val image = list[position]
        holder.bind(image, position)
    }

    companion object {
        val backgrList: List<String> = (1..9).map { i ->
            "anh_backgr/img_$i.png"
        }
    }
}
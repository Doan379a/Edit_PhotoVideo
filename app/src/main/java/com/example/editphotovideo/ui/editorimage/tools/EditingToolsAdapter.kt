package com.example.editphotovideo.ui.editorimage.tools

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.editphotovideo.R

class EditingToolsAdapter(val context: Context, private val mOnItemSelected: OnItemSelected) :
    RecyclerView.Adapter<EditingToolsAdapter.ViewHolder>() {

    var selectedIndex = -1
    private val mToolList = ToolModel.values().toList()

    interface OnItemSelected {
        fun onToolSelected(toolType: ToolType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_editing_tools, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mToolList[position]
        holder.txtTool.text = context.getString(item.titleResId)
        holder.imgToolIcon.setImageResource(item.iconResId)
        holder.imgToolIcon.setColorFilter(
            if (selectedIndex == position) Color.parseColor("#A0E12E") else
                Color.parseColor("#FFFFFF")
        )
        holder.txtTool.setTextColor(
            if (selectedIndex == position) Color.parseColor("#A0E12E") else
                Color.parseColor("#FFFFFF")
        )
    }

    override fun getItemCount(): Int {
        return mToolList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgToolIcon: ImageView = itemView.findViewById(R.id.imgToolIcon)
        val txtTool: TextView = itemView.findViewById(R.id.txtTool)

        init {
            itemView.setOnClickListener { _: View? ->
                val previousIndex = selectedIndex
                selectedIndex = layoutPosition
                mOnItemSelected.onToolSelected(mToolList[layoutPosition].toolType)
                notifyItemChanged(previousIndex)
                notifyItemChanged(selectedIndex)
            }
        }
    }

    fun resetSelection() {
        selectedIndex = -1
        notifyDataSetChanged()
    }

    enum class ToolModel(val titleResId: Int, val iconResId: Int, val toolType: ToolType) {
        BEAUTY(R.string.beauty, R.drawable.ic_beauty, ToolType.BEAUTY),
        CROP(R.string.crop, R.drawable.ic_crop, ToolType.CROP),
        LIGHTING(R.string.lighting, R.drawable.ic_light, ToolType.LIGHTING),
        SHAPE(R.string.shape, R.drawable.ic_shape, ToolType.SHAPE),
        TEXT(R.string.text, R.drawable.ic_text, ToolType.TEXT),
        ERASER(R.string.eraser, R.drawable.ic_eraser, ToolType.ERASER),
        FILTER(R.string.filter, R.drawable.ic_photo_filter, ToolType.FILTER),
        EMOJI(R.string.emoji, R.drawable.ic_insert_emoticon, ToolType.EMOJI),
        STICKER(R.string.sticker, R.drawable.ic_sticker, ToolType.STICKER),
    }
}
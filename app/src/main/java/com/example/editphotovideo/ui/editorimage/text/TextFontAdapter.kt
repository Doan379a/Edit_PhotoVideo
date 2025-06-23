package com.example.editphotovideo.ui.editorimage.text

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.editphotovideo.R
import com.example.editphotovideo.databinding.RowTextFontBinding
import com.example.editphotovideo.utils.getTypefaceFromAsset


class TextFontAdapter(
    val context: Context,
     fontList: List<String>,
    private val onFontClickListener: (Typeface) -> Unit
) : RecyclerView.Adapter<TextFontAdapter.FonViewHolder>() {


    private val updatedFontList = listOf("Default") + fontList

    var selectedFont: Typeface? = Typeface.DEFAULT

    inner class FonViewHolder(private val binding: RowTextFontBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(font: String, isCheck: Boolean) {
            val typeface = context.getTypefaceFromAsset(context,font)

            binding.fontNameTextView.typeface = typeface
            binding.fontNameTextView.text =
                if (font == "Default") "Default"
                else font.substringAfterLast("/").substringBeforeLast(".").replace("_", " ")
                    .replaceFirstChar { it.uppercase() }

            if (isCheck) {
                binding.layoutItem.setBackgroundResource(R.drawable.boder_selected_blur)
            } else {
                binding.layoutItem.setBackgroundResource(0)
            }

            binding.fontNameTextView.setOnClickListener {
                typeface?.let { it1 -> onFontClickListener(it1) }
                selectedFont = typeface
                notifyDataSetChanged()
            }
        }
    }

    fun updateSelectedFont(font: Typeface) {
        selectedFont = font
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FonViewHolder {
        val binding =
            RowTextFontBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FonViewHolder(binding)
    }

    override fun getItemCount(): Int = updatedFontList.size

    override fun onBindViewHolder(holder: FonViewHolder, position: Int) {
        val currentFont = updatedFontList[position]
        val typeface = if (currentFont == "Default") {
            Typeface.DEFAULT
        } else {
            try {
                Typeface.createFromAsset(holder.itemView.context.assets, currentFont)
            } catch (e: Exception) {
                Typeface.DEFAULT
            }
        }
        val isChecked = typeface == selectedFont
        holder.bind(currentFont, isChecked)
    }
}

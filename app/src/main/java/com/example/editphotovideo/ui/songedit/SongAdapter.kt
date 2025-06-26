package com.example.editphotovideo.ui.songedit

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.editphotovideo.R
import com.example.editphotovideo.base.BaseAdapter
import com.example.editphotovideo.data.MusicData
import com.example.editphotovideo.data.SongData
import com.example.editphotovideo.databinding.MusicListItemsBinding

class SongAdapter(
    val context: Context,
    val onClick: (song: SongData) -> Unit
) : BaseAdapter<MusicListItemsBinding, SongData>() {
    private var selectedPosition = 0
    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): MusicListItemsBinding {
        return MusicListItemsBinding.inflate(inflater, parent, false)
    }

    override fun creatVH(binding: MusicListItemsBinding): RecyclerView.ViewHolder =
        SongVH(binding)

    inner class SongVH(binding: MusicListItemsBinding) : BaseVH<SongData>(binding) {
        override fun onItemClickListener(data: SongData) {
            super.onItemClickListener(data)
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                val previousSelected = selectedPosition
                selectedPosition = pos
                notifyItemChanged(previousSelected)
                notifyItemChanged(selectedPosition)
                onClick.invoke(data)
            }
        }

        override fun bind(data: SongData) {
            super.bind(data)
            binding.txtMusicName.text = data.track_Title
            binding.txtMusicId.text = context.getString(R.string.demo_music)
            binding.radioMusicName.isChecked = adapterPosition == selectedPosition

            binding.rootView.setOnClickListener {
                onItemClickListener(data)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<SongData>) {
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
        if (listData.isNotEmpty()) {
            onClick.invoke(listData[0])
        }
    }
}
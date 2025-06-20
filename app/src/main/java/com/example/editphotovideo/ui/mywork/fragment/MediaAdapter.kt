package com.example.editphotovideo.ui.mywork.fragment

import android.media.MediaMetadataRetriever
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.editphotovideo.R
import java.io.File
import java.util.concurrent.TimeUnit

class MediaAdapter(
    private val mediaList: MutableList<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<MediaAdapter.MediaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_media, parent, false)
        return MediaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val media = mediaList[position]
        holder.bind(media)
    }

    override fun getItemCount(): Int = mediaList.size

    inner class MediaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mediaImageView: ImageView = itemView.findViewById(R.id.mediaImageView)
        private val mediaTypeTextView: TextView = itemView.findViewById(R.id.mediaTypeTextView)

        fun bind(media: String) {
            Glide.with(itemView.context)
                .load(media)
                .placeholder(R.drawable.img_loadding)
                .into(mediaImageView)

            val extension = File(media).extension.lowercase()

            if (extension in listOf("mp4", "avi", "mkv")) {
                val retriever = MediaMetadataRetriever()
                try {
                    retriever.setDataSource(media)
                    val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                    val durationMs = durationStr?.toLongOrNull() ?: 0L
                    mediaTypeTextView.visibility = View.VISIBLE
                    mediaTypeTextView.text = formatDuration(durationMs)
                } catch (e: Exception) {
                    e.printStackTrace()
                    mediaTypeTextView.text = ""
                    mediaTypeTextView.visibility = View.GONE
                } finally {
                    retriever.release()
                }
            } else {
                mediaTypeTextView.text = ""
                mediaTypeTextView.visibility = View.GONE
            }

            itemView.setOnClickListener {
                onItemClick(media)
            }
        }

        private fun formatDuration(durationMs: Long): String {
            val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMs)
            val seconds = TimeUnit.MILLISECONDS.toSeconds(durationMs) % 60
            return String.format("%02d:%02d", minutes, seconds)
        }
    }

    fun updateData(newList: List<String>) {
        mediaList.clear()
        mediaList.addAll(newList)
        notifyDataSetChanged()
    }
}


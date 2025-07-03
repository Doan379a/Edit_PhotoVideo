package com.videomaker.photovideo.editvideo.ui.editorimage.filters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Pair
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.videomaker.photovideo.editvideo.R
import com.videomaker.photovideo.editvideo.databinding.RowFilterViewBinding
import ja.burhanrashid52.photoeditor.PhotoFilter
import java.io.IOException
import java.util.ArrayList


class FilterViewAdapter(private val mFilterListener: FilterListener) :
    RecyclerView.Adapter<FilterViewAdapter.ViewHolder>() {

    private val mPairList: MutableList<Pair<String, PhotoFilter>> = ArrayList()
    private var selectedItemPosition = -1
    private var isItem0Modified = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowFilterViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filterPair = mPairList[position]
        val fromAsset = getBitmapFromAsset(holder.binding.root.context, filterPair.first)
        val isSelected = position == selectedItemPosition
        val isNoneFilter = filterPair.second == PhotoFilter.NONE
        if (position == 0 && isItem0Modified) {
            holder.binding.imgFilterView.setImageResource(R.drawable.img_none_selected) // Ảnh thay đổi
            holder.binding.txtFilterName.setTextColor(Color.parseColor("#A0E12E"))
        } else if (isNoneFilter) {
            val drawableResId =
                if (isSelected) R.drawable.img_none_selected else R.drawable.img_none
            holder.binding.imgFilterView.setImageResource(drawableResId)
            holder.binding.txtFilterName.setTextColor(Color.parseColor("#A0E12E"))
        } else {
            if (fromAsset != null) {
                holder.binding.imgFilterView.setImageBitmap(fromAsset)
            } else {
                holder.binding.imgFilterView.setImageResource(R.drawable.img_none)
            }
        }
        val textColor = if (isSelected) Color.parseColor("#A0E12E") else Color.parseColor("#FFFFFF")
        holder.binding.txtFilterName.setTextColor(textColor)

        holder.binding.txtFilterName.text =
            filterPair.second.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() }

        holder.binding.cardBoder.setCardBackgroundColor(
            if (isSelected && !isNoneFilter) Color.parseColor("#A0E12E") else Color.parseColor("#939393")
        )
//        val params = holder.binding.card.layoutParams
//        if (params is ViewGroup.MarginLayoutParams) {
//            if (isNoneFilter && isSelected) {
//                params.setMargins(0, 0, 0, 0)
//            } else {
//                val margin = holder.binding.card.context.resources.getDimensionPixelSize(R.dimen.mg_filter)
//                params.setMargins(margin, margin, margin, margin)
//            }
//            holder.binding.card.layoutParams = params
//        }

    }

    override fun getItemCount(): Int {
        return mPairList.size
    }

    inner class ViewHolder(val binding: RowFilterViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (layoutPosition == 0) {
                    // Nếu click vào item 0, thay đổi trạng thái của item 0
                    isItem0Modified = !isItem0Modified
                } else {
                    // Nếu click vào item khác, reset item 0 về trạng thái ban đầu
                    if (isItem0Modified) {
                        isItem0Modified = false
                    }
                }
                selectedItemPosition = layoutPosition
                notifyDataSetChanged()
                mFilterListener.onFilterSelected(mPairList[layoutPosition].second)
            }
        }
    }

    private fun getBitmapFromAsset(context: Context, strName: String): Bitmap? {
        val assetManager = context.assets
        return try {
            val istr = assetManager.open(strName)
            BitmapFactory.decodeStream(istr)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun setupFilters() {
        mPairList.add(Pair("filters/original.webp", PhotoFilter.NONE))
        mPairList.add(Pair("filters/auto_fix.webp", PhotoFilter.AUTO_FIX))
        mPairList.add(Pair("filters/brightness.webp", PhotoFilter.BRIGHTNESS))
        mPairList.add(Pair("filters/contrast.webp", PhotoFilter.CONTRAST))
        mPairList.add(Pair("filters/documentary.webp", PhotoFilter.DOCUMENTARY))
        mPairList.add(Pair("filters/dual_tone.webp", PhotoFilter.DUE_TONE))
        mPairList.add(Pair("filters/fill_light.webp", PhotoFilter.FILL_LIGHT))
        mPairList.add(Pair("filters/fish_eye.webp", PhotoFilter.FISH_EYE))
        mPairList.add(Pair("filters/grain.webp", PhotoFilter.GRAIN))
        mPairList.add(Pair("filters/gray_scale.webp", PhotoFilter.GRAY_SCALE))
        mPairList.add(Pair("filters/lomish.webp", PhotoFilter.LOMISH))
        mPairList.add(Pair("filters/negative.webp", PhotoFilter.NEGATIVE))
        mPairList.add(Pair("filters/posterize.webp", PhotoFilter.POSTERIZE))
        mPairList.add(Pair("filters/saturate.webp", PhotoFilter.SATURATE))
        mPairList.add(Pair("filters/sepia.webp", PhotoFilter.SEPIA))
        mPairList.add(Pair("filters/sharpen.webp", PhotoFilter.SHARPEN))
        mPairList.add(Pair("filters/temprature.webp", PhotoFilter.TEMPERATURE))
        mPairList.add(Pair("filters/tint.webp", PhotoFilter.TINT))
        mPairList.add(Pair("filters/vignette.webp", PhotoFilter.VIGNETTE))
        mPairList.add(Pair("filters/cross_process.webp", PhotoFilter.CROSS_PROCESS))
        mPairList.add(Pair("filters/b_n_w.webp", PhotoFilter.BLACK_WHITE))
        mPairList.add(Pair("filters/flip_horizental.webp", PhotoFilter.FLIP_HORIZONTAL))
        mPairList.add(Pair("filters/flip_vertical.webp", PhotoFilter.FLIP_VERTICAL))
        mPairList.add(Pair("filters/rotate.webp", PhotoFilter.ROTATE))
    }

    init {
        setupFilters()
    }
}
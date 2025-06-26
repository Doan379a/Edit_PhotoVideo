package com.example.editphotovideo.ui.editorimage.sticker

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.editphotovideo.R
import com.example.editphotovideo.databinding.FragmentBottomStickerEmojiDialogBinding
import com.example.editphotovideo.databinding.RowStickerBinding
import com.example.editphotovideo.ui.editorimage.BottomSheetDismissListener
import com.example.editphotovideo.utils.getBitmapFromAsset
import com.example.editphotovideo.widget.tap
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class StickerBSFragment : BottomSheetDialogFragment() {
    private var mStickerListener: StickerListener? = null
    private var dismissListener: BottomSheetDismissListener? = null
    private lateinit var binding: FragmentBottomStickerEmojiDialogBinding

    fun setStickerListener(stickerListener: StickerListener?) {
        mStickerListener = stickerListener
    }

    fun setDismissListener(listener: BottomSheetDismissListener) {
        dismissListener = listener
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissListener?.onBottomSheetDismissed()
    }

    interface StickerListener {
        fun onStickerClick(bitmap: Bitmap)
    }

    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        binding = FragmentBottomStickerEmojiDialogBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        val params = (binding.root.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior
        if (behavior is BottomSheetBehavior<*>) {
            behavior.addBottomSheetCallback(mBottomSheetBehaviorCallback)
        }

        (binding.root.parent as View).setBackgroundColor(resources.getColor(android.R.color.white))
        binding.root.setBackgroundResource(R.drawable.bg_sticker)

        val gridLayoutManager = GridLayoutManager(activity, 4)
        binding.rvEmoji.layoutManager = gridLayoutManager
        val stickerAdapter = StickerAdapter()
        binding.rvEmoji.adapter = stickerAdapter
        binding.rvEmoji.setHasFixedSize(true)
        binding.rvEmoji.setItemViewCacheSize(stickerPathList.size)
        binding.tvTitle.text = getString(R.string.sticker)
        binding.imgDone.tap {
            dismiss()
        }
    }

    inner class StickerAdapter : RecyclerView.Adapter<StickerAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding =
                RowStickerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val sticker = stickerPathList[position]
            val fromAsset = requireActivity().getBitmapFromAsset(requireActivity(), sticker)
            Glide.with(requireActivity())
                .asBitmap()
                .load(fromAsset)
                .into(holder.binding.imgSticker)
        }

        override fun getItemCount(): Int = stickerPathList.size

        inner class ViewHolder(val binding: RowStickerBinding) :
            RecyclerView.ViewHolder(binding.root) {
            init {
                binding.root.setOnClickListener {
                    val sticker = stickerPathList[adapterPosition]
                    val fromAsset = requireActivity().getBitmapFromAsset(requireActivity(), sticker)
                    Glide.with(requireActivity())
                        .asBitmap()
                        .load(fromAsset)
                        .into(object : CustomTarget<Bitmap?>(256, 256) {
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap?>?
                            ) {
                                mStickerListener?.onStickerClick(resource)
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {}
                        })
                    dismiss()
                }
            }
        }
    }

    companion object {
        private val stickerPathList: List<String> by lazy {
            val baseStickers = listOf(
                "sticker/food/food_birthday_cake_1.webp",
                "sticker/food/food_birthday_cake_2.webp",
                "sticker/food/food_birthday_cake_3.webp",
                "sticker/food/food_cupcake.webp",
                "sticker/food/food_cupcake_with_cherry.webp",
                "sticker/food/food_ice_cream_1.webp",
                "sticker/food/food_ice_cream2.webp",
                "sticker/food/food_ice_cream3.webp",
                "sticker/food/food_ice_cream_4.webp",
                "sticker/food/food_pizza1.webp",
                "sticker/food/food_pizza2.webp",
                ////

                "sticker/fun/fun_rainbow.webp",
                "sticker/fun/fun_star.webp",
                "sticker/fun/fun_star2.webp",
                ////
                "sticker/words/words_bang.webp",
                "sticker/words/words_yeah.webp",
                "sticker/words/words_blog.webp",
                "sticker/words/words_boom.webp",
                "sticker/words/words_happy_birthday1.webp",
                "sticker/words/words_happy_birthday2.webp",
                "sticker/words/words_happy_birthday3.webp",
                "sticker/words/words_legacy.webp",
                "sticker/words/words_win1.webp",
                "sticker/words/words_wow.webp",
                /////
                "sticker/facial/facial_1.webp",
                "sticker/facial/facial_2.webp",
                "sticker/facial/facial_3.webp",
                "sticker/facial/facial_4.webp",
                "sticker/facial/facial_5.webp",
                "sticker/facial/facial_6.webp",
                "sticker/facial/facial_7.webp",
                "sticker/facial/facial_8.webp",
                "sticker/facial/facial_9.webp",
                "sticker/facial/facial_10.webp",
                "sticker/facial/facial_11.webp",
                "sticker/facial/facial_12.webp",
                "sticker/facial/facial_13.webp",
                ///
                "sticker/animal/animal_cat1.webp",
                "sticker/animal/animal_deer1.webp",
                "sticker/animal/animal_dog1.webp",
                "sticker/animal/animal_fox1.webp",
                "sticker/animal/animal_bee1.webp",
                "sticker/animal/animal_frog1.webp",
                "sticker/animal/animal_hippo1.webp",
                "sticker/animal/animal_fox2.webp",
                "sticker/animal/animal_hen.webp",
                "sticker/animal/animal_donkey.webp",
            )

            //stickerv2
            val generated = (1..236).map { i ->
                "sticker/stickerv1/sticker_$i.webp"
            }
            (baseStickers + generated).reversed()
        }
    }
}

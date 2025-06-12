package com.example.editphotovideo.ui.editmovie

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.databinding.ActivityImageAlbumBinding
import com.example.editphotovideo.widget.tap

class ImageEditActivity : BaseActivity<ActivityImageAlbumBinding>() {
    private lateinit var application: MyApplication
    private lateinit var imageEditAdapter: ImageEditAdapter
    private var isFromPreview: Boolean = false
    private var selectedUris: ArrayList<Uri>? = null
    override fun setViewBinding(): ActivityImageAlbumBinding {
        return ActivityImageAlbumBinding.inflate(layoutInflater)
    }

    override fun initView() {
        application = MyApplication.getInstance()
        application.isEditModeEnable = true
        isFromPreview = intent.hasExtra("extra_from_preview")
        selectedUris = intent.getParcelableArrayListExtra("selectedImages")
        setupRecyclerView()
    }

    override fun viewListener() {
        binding.ivDone.tap {
            done()
        }
    }

    override fun dataObservable() {
    }


    private fun done() {
        application.isEditModeEnable = false
        if (this.isFromPreview) {
            setResult(-1)
            finish()
            return
        }
        val intent = Intent(this, PreviewActivity::class.java)
            .putExtra("selectedImages", selectedUris)
        startActivity(intent)
    }
    private val _ithCallback = object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG, ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            imageEditAdapter.swap(viewHolder.adapterPosition, target.adapterPosition)
            application.min_pos = minOf(application.min_pos, viewHolder.adapterPosition, target.adapterPosition)
            MyApplication.isBreak = true
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
    }

    private fun setupRecyclerView() {
        binding.rvVideoAlbum.apply {
            layoutManager = GridLayoutManager(this@ImageEditActivity, 2)
            itemAnimator = DefaultItemAnimator()
            imageEditAdapter = ImageEditAdapter(this@ImageEditActivity)
            adapter = imageEditAdapter
            setEmptyView(binding.listEmpty)
        }

        ItemTouchHelper(_ithCallback).attachToRecyclerView(binding.rvVideoAlbum)
    }

}

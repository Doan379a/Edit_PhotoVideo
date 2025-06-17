package com.example.editphotovideo.ui.main.template.select

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.editphotovideo.R
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.databinding.ActivitySelectTemplateBinding
import com.example.editphotovideo.library.zoomrcv.ZoomRecyclerLayout
import com.example.editphotovideo.ui.main.template.adapter.SelectTemplateAdapter
import com.example.editphotovideo.ui.main.template.adapter.getSnapPosition
import com.example.editphotovideo.ui.main.template.model.Template
import com.example.editphotovideo.ui.main.template.model.TemplateType
import com.example.editphotovideo.ui.main.template.model.getTemplateAutumn
import com.example.editphotovideo.ui.main.template.model.getTemplateHaloween
import com.example.editphotovideo.ui.main.template.model.getTemplateNeon
import com.example.editphotovideo.ui.main.template.model.getTemplateNoel
import com.example.editphotovideo.ui.main.template.model.getTemplateTrending
import com.example.editphotovideo.ui.main.template.model.getTemplateWedding
import com.example.editphotovideo.ui.removebackgr.RemoveBackGrActivity
import com.example.editphotovideo.widget.getTagDebug
import com.example.editphotovideo.widget.tap
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlin.text.Typography.section

class SelectTemplateActivity : BaseActivity<ActivitySelectTemplateBinding>() {
    private lateinit var listTemplate: List<Template>
    private var templateId: String? = null
    private lateinit var adapter2: SelectTemplateAdapter
    override fun setViewBinding(): ActivitySelectTemplateBinding {
        return ActivitySelectTemplateBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val sectionTitle = intent.getStringExtra("sectionTitle")
        templateId = intent.getStringExtra("templateId")
        val type = intent.getSerializableExtra("type") as? TemplateType
        Log.d(getTagDebug(), "Section: $section, TemplateId: $templateId, Type: $type")
        if (type != null && templateId != null && sectionTitle != null) {
            checkSection(sectionTitle, type, templateId!!)
            setUpRcv()
            setUpScollRcv(templateId!!)
        }
    }

    override fun viewListener() {
        binding.imgClose.tap {
            finish()
        }
        binding.tvSelectTemplate.tap {
            removeBackGround()
        }
    }

    private fun checkSection(sectionTitle: String, section: TemplateType, templateId: String) =
        binding.apply {
            when (section) {
                TemplateType.TRENDING -> {
                    val list = getTemplateTrending()
                    listTemplate = list
                    tvTitle.text = getString(R.string.trending)
                }

                TemplateType.AUTUMN -> {
                    val list = getTemplateAutumn()
                    listTemplate = list
                    tvTitle.text = getString(R.string.autumn)
                }

                TemplateType.NOEL -> {
                    val list = getTemplateNoel()
                    listTemplate = list
                    tvTitle.text = getString(R.string.noel)
                }

                TemplateType.HALOWEEN -> {
                    val list = getTemplateHaloween()
                    listTemplate = list
                    tvTitle.text = getString(R.string.haloween)
                }

                TemplateType.NEON -> {
                    val list = getTemplateNeon()
                    listTemplate = list
                    tvTitle.text = getString(R.string.neon)
                }

                TemplateType.WEDDING -> {
                    val list = getTemplateWedding()
                    listTemplate = list
                    tvTitle.text = getString(R.string.wedding)
                }
            }
            Log.d(
                getTagDebug(),
                "Section: $section, TemplateId: $templateId,listTemplate:$listTemplate"
            )
        }

    private fun setUpRcv() = binding.recyclerView.apply {
         adapter2 = SelectTemplateAdapter(this@SelectTemplateActivity, listTemplate)
        val linearLayoutManager = ZoomRecyclerLayout(this@SelectTemplateActivity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutManager.reverseLayout = false
        linearLayoutManager.stackFromEnd = true
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(this)
        isNestedScrollingEnabled = false
        clipToPadding = false
        layoutManager = linearLayoutManager
        adapter = adapter2
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val snapPos = recyclerView.getSnapPosition(snapHelper)
                    val template = adapter2.getTemplateAt(snapPos)
                    templateId= template?.id.toString()
                    Log.d("SnapItem", "Template ID trung tâm: ${template?.id}")
                }
            }
        })

    }

    private fun setUpScollRcv(templateId: String) {
        val selectedTemplateId = templateId.toIntOrNull()
        if (selectedTemplateId != null) {
            val selectedIndex = listTemplate.indexOfFirst { it.id == selectedTemplateId }
            if (selectedIndex != -1) {
                binding.recyclerView.scrollToPosition(selectedIndex)
            }
        }
    }

    override fun dataObservable() {
    }

    private fun removeBackGround() {
        TedImagePicker.with(this)
            .cancelListener {
                Log.d("TedImagePicker", "Người dùng đã hủy chọn ảnh")
            }
            .errorListener {
                Log.d("TedImagePicker", "Lỗi khi chọn ảnh!")
            }
            .start { uri ->
                val intent = Intent(this, RemoveBackGrActivity::class.java).apply {
                    putExtra(
                        "URI_IMAGE_INPUT",
                        uri
                    )
                    putExtra("templateId", templateId)
                }
                startActivity(intent)
            }
    }
}
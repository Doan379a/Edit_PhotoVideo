package com.example.editphotovideo.ui.save

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.bumptech.glide.Glide
import com.example.editphotovideo.base.BaseActivity
import com.example.editphotovideo.databinding.ActivitySaveImageBinding
import com.example.editphotovideo.ui.editorimage.EditImageActivity
import com.example.editphotovideo.ui.main.MainActivity
import com.example.editphotovideo.utils.ShareUtils
import com.example.editphotovideo.utils.ShareUtils.shareImage
import com.example.editphotovideo.widget.tap
import gun0912.tedimagepicker.builder.TedImagePicker


class SaveImageActivity : BaseActivity<ActivitySaveImageBinding>() {

    private var imageUri: String? = null
    override fun setViewBinding(): ActivitySaveImageBinding {
        return ActivitySaveImageBinding.inflate(layoutInflater)
    }

    override fun initView() {
        imageUri = intent.getStringExtra("output_image_uri")
        Glide.with(this).load(imageUri).centerCrop().into(binding.imgResult)
    }

    override fun viewListener() {
        binding.imgBack.tap {
            finish()
        }
        binding.imgHome.tap {
            showActivity(MainActivity::class.java)
            finishAffinity()
        }
        binding.imgFaceBook.tap {
            shareImage(this, ShareUtils.KeyShare.FACEBOOK, Uri.parse(imageUri))
        }
        binding.imgShareMore.tap {
            shareImage(this, ShareUtils.KeyShare.SHARE_MORE, Uri.parse(imageUri))
        }
        binding.imgInstagram.tap {
            shareImage(this, ShareUtils.KeyShare.INSTAGRAM, Uri.parse(imageUri))
        }
        binding.imgTiktok.tap {
            shareImage(this, ShareUtils.KeyShare.TIKTOK, Uri.parse(imageUri))
        }
        binding.imgYoutube.tap {
            shareImage(this, ShareUtils.KeyShare.YOUTUBE, Uri.parse(imageUri))
        }
        binding.imgMessenger.tap {
            shareImage(this, ShareUtils.KeyShare.MESSENGER, Uri.parse(imageUri))
        }
        binding.imgWhatsapp.tap {
            shareImage(this, ShareUtils.KeyShare.WHATSAPP, Uri.parse(imageUri))
        }
        binding.tvNewProject.tap { selectImageEdit() }
    }

    private fun selectImageEdit() {
        TedImagePicker.with(this)
            .cancelListener {
                Log.d("TedImagePicker", "Người dùng đã hủy chọn ảnh")
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finishAffinity()
            }
            .errorListener {
                Log.d("TedImagePicker", "Lỗi khi chọn ảnh!")
            }
            .start { uri ->
                val intent = Intent(this, EditImageActivity::class.java).apply {
                    putExtra(
                        "URI_IMAGE_INPUT",
                        uri
                    )
                }

                startActivity(intent)
                finish()
            }
    }
    override fun dataObservable() {
    }
}
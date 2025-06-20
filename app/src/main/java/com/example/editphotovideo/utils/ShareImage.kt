package com.example.editphotovideo.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.compose.ui.input.key.Key
import com.example.editphotovideo.R

object ShareImage {
    enum class KeyShare {
        WHATSAPP,
        FACEBOOK,
        INSTAGRAM,
        MESSENGER,
        TIKTOK,
        YOUTUBE,
        EMAIL,
        SHARE_MORE
    }
    private const val VIDEO_SHARE = "video"
    private const val IMAGE_SHARE = "image"


    private const val FACEBOOK = "com.facebook.katana"
    private const val MESSENGER = "com.facebook.orca"
    private const val WHATSAPP = "com.whatsapp"
    private const val INSTAGRAM = "com.instagram.android"
    private const val TIKTOK_GLOBAL = "com.ss.android.ugc.trill"      // Phiên bản toàn cầu
    private const val TIKTOK_REGION =
        "com.zhiliaoapp.musically"       // Phiên bản khu vực :contentReference[oaicite:1]{index=1}
    private const val YOUTUBE = "com.google.android.youtube"

    fun shareVideo(context: Context, target: KeyShare, videoUri: Uri) {
        shareData(context, mediaKey = VIDEO_SHARE, target = target, uri = videoUri)
    }

    fun shareImage(context: Context, target: KeyShare, imageUri: Uri) {
        shareData(context, mediaKey = IMAGE_SHARE, target = target, uri = imageUri)
    }

    private fun shareData(context: Context, mediaKey: String, target: KeyShare, uri: Uri) {
        val mime = if (mediaKey == IMAGE_SHARE) "image/*" else "video/mp4"

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = mime
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }


        when (target) {
            KeyShare.FACEBOOK -> {
                if (isAppInstalled(context, FACEBOOK)) {
                    shareIntent.setPackage(FACEBOOK)
                } else {
                    openPlayStore(context, FACEBOOK); return
                }
            }

            KeyShare.INSTAGRAM -> {
                if (isAppInstalled(context, INSTAGRAM)) {
                    shareIntent.setPackage(INSTAGRAM)
                } else {
                    openPlayStore(context, INSTAGRAM); return
                }
            }

            KeyShare.WHATSAPP -> {
                if (isAppInstalled(context, WHATSAPP)) {
                    shareIntent.setPackage(WHATSAPP)
                } else {
                    openPlayStore(context, WHATSAPP); return
                }
            }

            KeyShare.MESSENGER -> {
                if (isAppInstalled(context, MESSENGER)) {
                    shareIntent.setPackage(MESSENGER)
                } else {
                    openPlayStore(context, MESSENGER); return
                }
            }

            KeyShare.TIKTOK -> {
                when {
                    isAppInstalled(context, TIKTOK_GLOBAL) ->
                        shareIntent.setPackage(TIKTOK_GLOBAL)

                    isAppInstalled(context, TIKTOK_REGION) ->
                        shareIntent.setPackage(TIKTOK_REGION)

                    else -> {
                        openPlayStore(context, TIKTOK_GLOBAL); return
                    }
                }
            }

            KeyShare.YOUTUBE -> {
                if (isAppInstalled(context, YOUTUBE)) {
                    shareIntent.setPackage(YOUTUBE)
                } else {
                    openPlayStore(context, YOUTUBE)
                    return
                }
            }

            KeyShare.EMAIL -> {
                shareIntent.setType("message/rfc822")
            }

            KeyShare.SHARE_MORE -> {
                // share chung với chooser, không setPackage
            }
        }
        val chooserTitle = when (mediaKey) {
            IMAGE_SHARE -> context.getString(R.string.share_image)
            else -> context.getString(R.string.share_video)
        }
        if (shareIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(
                Intent.createChooser(
                    shareIntent,
                    chooserTitle
                )
            )
        } else {
            Log.e("ShareActivity", "No app available for $target")
        }
    }

    private fun isAppInstalled(context: Context, packageName: String): Boolean {
        return try {
            context.packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun openPlayStore(context: Context, packageName: String) {
        val playStoreIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
        playStoreIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(playStoreIntent)
    }
}

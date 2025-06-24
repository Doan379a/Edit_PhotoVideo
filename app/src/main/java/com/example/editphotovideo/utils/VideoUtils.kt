package com.example.editphotovideo.utils

import android.app.PendingIntent
import android.app.RecoverableSecurityException
import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import com.example.editphotovideo.utils.ImageUtils.DEFAULT_FOLDER
import java.io.File

object VideoUtils {
     fun saveAudioToMusic(context: Context,sourceFile: File, displayName: String): Pair<Uri?,Boolean> {
        return try {
            val values = android.content.ContentValues().apply {
                put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, displayName)
                put(android.provider.MediaStore.MediaColumns.MIME_TYPE, "audio/mp3")
                put(android.provider.MediaStore.MediaColumns.RELATIVE_PATH, "${android.os.Environment.DIRECTORY_MUSIC}/$DEFAULT_FOLDER")
            }

            val uri = context.contentResolver.insert(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values)
                ?: return  Pair(null, false)

            context.contentResolver.openOutputStream(uri)?.use { output ->
                sourceFile.inputStream().copyTo(output)
            }

            Pair(uri, true)
        } catch (e: Exception) {
            Log.e("ExtractAudio", "Lỗi khi lưu vào MediaStore", e)
            Pair(null, false)
        }
    }
    fun getDisplayNameFromUri(context: Context, uri: Uri): String? {
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst() && nameIndex != -1) {
                return cursor.getString(nameIndex)
            }
        }
        return null
    }
    fun buildDeleteIntentSender(
        context: Context,
        uri: Uri,
        onResult: (IntentSenderRequest?, Boolean) -> Unit
    ) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                // Android 11 trở lên
                val intentSender = MediaStore.createDeleteRequest(
                    context.contentResolver,
                    listOf(uri)
                ).intentSender
                val request = IntentSenderRequest.Builder(intentSender).build()
                onResult(request, false)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Android 10
                try {
                    context.contentResolver.delete(uri, null, null)
                    val dummyIntent = Intent()
                    val dummySender = PendingIntent.getActivity(
                        context,
                        0,
                        dummyIntent,
                        PendingIntent.FLAG_IMMUTABLE
                    ).intentSender
                    val request = IntentSenderRequest.Builder(dummySender).build()
                    onResult(request, true)
                    Log.d("TAO_NE", "Thanh cong ??")
                } catch (e: SecurityException) {
                    if (e is RecoverableSecurityException) {
                        val intentSender = e.userAction.actionIntent.intentSender
                        val request = IntentSenderRequest.Builder(intentSender).build()
                        onResult(request, false)
                    } else {
                        e.printStackTrace()
                        onResult(null, false)
                    }
                }
            } else {
                // Android 9 trở xuống
                context.contentResolver.delete(uri, null, null)
                onResult(null, false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onResult(null, false)
        }
    }

}
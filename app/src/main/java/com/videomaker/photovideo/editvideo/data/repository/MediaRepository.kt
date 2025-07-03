package com.videomaker.photovideo.editvideo.data.repository


import androidx.lifecycle.LiveData
import com.videomaker.photovideo.editvideo.data.dao.MediaDao
import com.videomaker.photovideo.editvideo.data.entity.MediaEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MediaRepository(private val mediaDao: MediaDao) {

    suspend fun insertMedia(media: MediaEntity) {
        mediaDao.insertMedia(media)
    }

    fun getAllMedia(): LiveData<List<MediaEntity>> {
        return mediaDao.getAllMediaLive()
    }


    suspend fun getMediaByPath(filePath: String): MediaEntity? {
        return mediaDao.getMediaByPath(filePath)
    }

    suspend fun deleteMedia(media: MediaEntity) {
        mediaDao.deleteMedia(media)
    }
}
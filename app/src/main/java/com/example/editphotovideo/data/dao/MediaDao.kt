package com.example.editphotovideo.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.editphotovideo.data.entity.MediaEntity

@Dao
interface MediaDao {

    @Insert
    suspend fun insertMedia(media: MediaEntity)

    @Query("SELECT * FROM media")
    fun getAllMediaLive(): LiveData<List<MediaEntity>>

    @Query("SELECT * FROM media WHERE filePath = :filePath")
    suspend fun getMediaByPath(filePath: String): MediaEntity?

    @Delete
    suspend fun deleteMedia(media: MediaEntity)
}

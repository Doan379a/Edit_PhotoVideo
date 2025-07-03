package com.videomaker.photovideo.editvideo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "media")
data class MediaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var filePath: String,
    val mediaType: MediaType
){
    constructor(filePath: String, mediaType: MediaType): this( 0,filePath, mediaType)
}

enum class MediaType {
    IMAGE,
    VIDEO
}
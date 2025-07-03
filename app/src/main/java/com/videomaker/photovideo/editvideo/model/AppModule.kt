package com.videomaker.photovideo.editvideo.model

import android.content.Context
import com.videomaker.photovideo.editvideo.data.DSDatabase
import com.videomaker.photovideo.editvideo.data.dao.MediaDao
import com.videomaker.photovideo.editvideo.data.repository.MediaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DSDatabase {
        return DSDatabase.getDatabase(context)
    }

    @Provides
    fun provideMediaDao(database: DSDatabase) = database.mediaDao()

    @Provides
    fun provideMediaRepository(mediaDao: MediaDao): MediaRepository {
        return MediaRepository(mediaDao)
    }
}

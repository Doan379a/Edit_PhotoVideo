package com.example.editphotovideo.model

import android.content.Context
import com.example.editphotovideo.data.DSDatabase
import com.example.editphotovideo.data.dao.MediaDao
import com.example.editphotovideo.data.repository.MediaRepository
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

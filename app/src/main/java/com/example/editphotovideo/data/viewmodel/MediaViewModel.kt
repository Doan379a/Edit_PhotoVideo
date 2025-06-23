package com.example.editphotovideo.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.editphotovideo.data.entity.MediaEntity
import com.example.editphotovideo.data.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(private val repository: MediaRepository) : ViewModel() {

    private val _shouldReloadMedia = MutableLiveData<Boolean>()
    val shouldReloadMedia: LiveData<Boolean> get() = _shouldReloadMedia
    val mediaList: LiveData<List<MediaEntity>> = repository.getAllMedia()

    fun notifyReloadMedia() {
        _shouldReloadMedia.value = true
    }

    fun insertMedia(media: MediaEntity) {
        viewModelScope.launch {
            repository.insertMedia(media)

        }
    }

    fun deleteMedia(media: MediaEntity) {
        viewModelScope.launch {
            repository.deleteMedia(media)

        }
    }
}

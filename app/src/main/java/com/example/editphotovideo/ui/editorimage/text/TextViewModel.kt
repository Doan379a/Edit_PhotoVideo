package com.example.editphotovideo.ui.editorimage.text

import android.graphics.Typeface
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TextViewModel : ViewModel() {
    private val _textFont = MutableLiveData<Typeface>()
    val textFont: MutableLiveData<Typeface> get() = _textFont

    private val _textSize = MutableLiveData<Float>()
    val textSize: MutableLiveData<Float> get() = _textSize

    fun setTextSize(size: Float) {
        _textSize.value = size
    }

    fun setTextFont(font: Typeface) {
        _textFont.value = font
    }
}
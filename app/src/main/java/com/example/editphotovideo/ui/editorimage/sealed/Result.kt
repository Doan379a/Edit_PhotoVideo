package com.example.editphotovideo.ui.editorimage.sealed

import android.graphics.Bitmap

sealed class CropResult {
    data class Success(val bitmap: Bitmap) : CropResult()
    object Canceled : CropResult()
    data class Error(val message: String) : CropResult()
}

sealed class ImageFilterResult {
    data class Success(val bitmap: Bitmap?) : ImageFilterResult()
    object Canceled : ImageFilterResult()
    data class Error(val message: String) : ImageFilterResult()
}

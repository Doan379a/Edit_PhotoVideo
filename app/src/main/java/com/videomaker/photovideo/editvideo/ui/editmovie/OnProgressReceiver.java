package com.videomaker.photovideo.editvideo.ui.editmovie;

public interface OnProgressReceiver {
    void onImageProgressFrameUpdate(float f);

    void onProgressFinish(String str);

    void onVideoProgressFrameUpdate(float f);
}

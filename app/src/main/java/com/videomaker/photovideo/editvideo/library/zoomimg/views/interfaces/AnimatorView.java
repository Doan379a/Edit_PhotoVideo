package com.videomaker.photovideo.editvideo.library.zoomimg.views.interfaces;

import androidx.annotation.NonNull;

import com.videomaker.photovideo.editvideo.library.zoomimg.animation.ViewPositionAnimator;

/**
 * Common interface for views supporting position animation.
 */
public interface AnimatorView {

    /**
     * @return {@link ViewPositionAnimator} instance to control animation from other view position.
     */
    @NonNull
    ViewPositionAnimator getPositionAnimator();

}

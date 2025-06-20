package com.example.editphotovideo.library.zoomimg.views.interfaces;

import androidx.annotation.NonNull;

import com.example.editphotovideo.library.zoomimg.GestureController;

/**
 * Common interface for all Gesture* views.
 * <p>
 * All classes implementing this interface should be descendants of {@link android.view.View}.
 */
public interface GestureView {

    /**
     * Returns {@link GestureController} which is a main engine for all gestures interactions.
     * <p>
     * Use it to apply settings, access and modify image state and so on.
     *
     * @return {@link GestureController}.
     */
    @NonNull
    GestureController getController();

}

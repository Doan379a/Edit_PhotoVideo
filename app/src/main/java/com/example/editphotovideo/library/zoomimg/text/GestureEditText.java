package com.example.editphotovideo.library.zoomimg.text;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.editphotovideo.library.zoomimg.GestureController;
import com.example.editphotovideo.library.zoomimg.State;
import com.example.editphotovideo.library.zoomimg.views.interfaces.GestureView;


/**
 * Sample of TextView with added gesture controls.
 */
public class GestureEditText extends AppCompatEditText implements GestureView {

    private final GestureController controller;

    private float origSize;
    private float size;

    public GestureEditText(Context context) {
        this(context, null, 0);
    }

    public GestureEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        controller = new GestureController(this);
        controller.getSettings().setOverzoomFactor(1f).setPanEnabled(false);
//        setTextIsSelectable(true);
        controller.getSettings().initFromAttributes(context, attrs);
        controller.addOnStateChangeListener(new GestureController.OnStateChangeListener() {
            @Override
            public void onStateChanged(State state) {
                applyState(state);
            }

            @Override
            public void onStateReset(State oldState, State newState) {
                applyState(newState);
            }
        });

        origSize = getTextSize();
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public GestureController getController() {
        return controller;
    }

    @SuppressLint("ClickableViewAccessibility") // performClick will be called by controller
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        return controller.onTouch(this, event);
    }

    @Override
    public void setTextSize(float size) {
        super.setTextSize(size);
        origSize = getTextSize();
        applyState(controller.getState());
    }

    @Override
    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        origSize = getTextSize();
        applyState(controller.getState());
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        controller.getSettings().setViewport(width, height).setImage(width, height);
        controller.updateState();
    }

    protected void applyState(State state) {
        float size = origSize * state.getZoom();
        float maxSize = origSize * controller.getStateController().getMaxZoom(state);
        size = Math.max(origSize, Math.min(size, maxSize));

        // Bigger text size steps for smoother scaling
        size = Math.round(size);

        if (!State.equals(this.size, size)) {
            this.size = size;
            super.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

}

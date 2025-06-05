package com.example.editphotovideo.library.zoomimg.transition;

import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.editphotovideo.library.zoomimg.transition.ViewsTransitionAnimator.RequestListener;
import com.example.editphotovideo.library.zoomimg.transition.internal.FromListViewListener;
import com.example.editphotovideo.library.zoomimg.transition.internal.FromRecyclerViewListener;
import com.example.editphotovideo.library.zoomimg.transition.internal.IntoViewPager2Listener;
import com.example.editphotovideo.library.zoomimg.transition.internal.IntoViewPagerListener;
import com.example.editphotovideo.library.zoomimg.transition.tracker.FromTracker;
import com.example.editphotovideo.library.zoomimg.transition.tracker.IntoTracker;
import com.example.editphotovideo.library.zoomimg.views.interfaces.AnimatorView;
/**
 * Transition animation builder.
 */
@SuppressWarnings("unused")
public class GestureTransitions<ID> {

    // ViewsTransitionAnimator' public constructor is temporary deprecated
    private final ViewsTransitionAnimator<ID> animator = new ViewsTransitionAnimator<>();

    private GestureTransitions() {}

    @NonNull
    public static <ID> GestureTransitions<ID> from(@NonNull RequestListener<ID> listener) {
        final GestureTransitions<ID> builder = new GestureTransitions<>();
        builder.animator.setFromListener(listener);
        return builder;
    }

    @NonNull
    public static <ID> GestureTransitions<ID> from(@NonNull final View view) {
        return from(new RequestListener<ID>() {
            @Override
            public void onRequestView(@NonNull ID id) {
                getAnimator().setFromView(id, view);
            }
        });
    }

    @NonNull
    public static <ID> GestureTransitions<ID> from(
            @NonNull RecyclerView recyclerView,
            @NonNull FromTracker<ID> tracker) {
        return from(recyclerView, tracker, true);
    }

    @NonNull
    public static <ID> GestureTransitions<ID> from(
            @NonNull RecyclerView recyclerView,
            @NonNull FromTracker<ID> tracker,
            boolean autoScroll) {
        return from(new FromRecyclerViewListener<>(recyclerView, tracker, autoScroll));
    }

    @NonNull
    public static <ID> GestureTransitions<ID> from(
            @NonNull ListView listView,
            @NonNull FromTracker<ID> tracker) {
        return from(listView, tracker, true);
    }

    @NonNull
    public static <ID> GestureTransitions<ID> from(
            @NonNull ListView listView,
            @NonNull FromTracker<ID> tracker,
            boolean autoScroll) {
        return from(new FromListViewListener<>(listView, tracker, autoScroll));
    }

    @NonNull
    public static <ID> GestureTransitions<ID> fromNone() {
        return from(new RequestListener<ID>() {
            @Override
            public void onRequestView(@NonNull ID id) {
                getAnimator().setFromNone(id);
            }
        });
    }


    @NonNull
    public ViewsTransitionAnimator<ID> into(@NonNull RequestListener<ID> listener) {
        animator.setToListener(listener);
        return animator;
    }

    @NonNull
    public ViewsTransitionAnimator<ID> into(@NonNull final AnimatorView view) {
        return into(new RequestListener<ID>() {
            @Override
            public void onRequestView(@NonNull ID id) {
                getAnimator().setToView(id, view);
            }
        });
    }

    @NonNull
    public ViewsTransitionAnimator<ID> into(
            @NonNull ViewPager viewPager,
            @NonNull IntoTracker<ID> tracker) {
        return into(new IntoViewPagerListener<>(viewPager, tracker));
    }

    @NonNull
    public ViewsTransitionAnimator<ID> into(
            @NonNull ViewPager2 viewPager,
            @NonNull IntoTracker<ID> tracker) {
        return into(new IntoViewPager2Listener<>(viewPager, tracker));
    }

}

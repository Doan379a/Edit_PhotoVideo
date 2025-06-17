package com.example.editphotovideo.library.zoomrcv

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class ZoomRecyclerLayout : LinearLayoutManager {

    private val mShrinkAmount = 0.15f
    private val mShrinkDistance = 0.9f

    constructor(context: Context) : super(context)

    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(
        context,
        orientation,
        reverseLayout
    )

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        return if (orientation == HORIZONTAL) {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
            applyZoomEffect()
            scrolled
        } else 0
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        return if (orientation == VERTICAL) {
            val scrolled = super.scrollVerticallyBy(dy, recycler, state)
            applyZoomEffect()
            scrolled
        } else 0
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        applyZoomEffect()
    }

    private fun applyZoomEffect() {
        val midpoint = if (orientation == HORIZONTAL) width / 2f else height / 2f
        val d0 = 0f
        val d1 = mShrinkDistance * midpoint
        val s0 = 1f
        val s1 = 1f - mShrinkAmount

        for (i in 0 until childCount) {
            val child = getChildAt(i) ?: continue
            val childMidpoint = if (orientation == HORIZONTAL) {
                (getDecoratedLeft(child) + getDecoratedRight(child)) / 2f
            } else {
                (getDecoratedTop(child) + getDecoratedBottom(child)) / 2f
            }

            val d = d1.coerceAtMost(abs(midpoint - childMidpoint))
            val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
            child.scaleX = scale
            child.scaleY = scale
        }
    }
}

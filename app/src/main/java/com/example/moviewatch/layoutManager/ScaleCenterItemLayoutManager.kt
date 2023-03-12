package com.example.moviewatch.layoutManager

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.min

class ScaleCenterItemLayoutManager : LinearLayoutManager {
    @Inject constructor(context: Context?, orientation: Int, reverseLayout: Boolean): super(context, orientation, reverseLayout)

    private var horizontalSpace = 0

    override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
        lp!!.width = width/2
        return true
    }

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        scaleMiddleItem()
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        horizontalSpace = width - paddingStart - paddingEnd
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            scaleMiddleItem()
        }
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        return if (orientation == HORIZONTAL){
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
            scaleMiddleItem()
            scrolled
        }else{
            0
        }

    }


    private fun scaleMiddleItem(){

        val mid = width/2
        val d1 = 0.9f * mid
        for (i in 0 until  childCount){
            val child = getChildAt(i)
            val childMid = (getDecoratedRight(child!!) + getDecoratedLeft(child!!))/ 2f
            val d = min(d1, abs(mid - childMid))
            val scale = 1-0.30f * d/d1
            child.scaleX = scale
            child.scaleY = scale
        }

    }
}
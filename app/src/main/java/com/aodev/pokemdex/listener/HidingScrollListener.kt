package com.aodev.pokemdex.listener

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class HidingScrollListener : RecyclerView.OnScrollListener() {
    private var mScrolledDistance = 0
    private var mControlsVisible = true
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val firstVisibleItem =
            (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
        if (firstVisibleItem == 0) {
            if (!mControlsVisible) {
                Log.e("HidingScrollListener", "if")
//                onShow()
                onFirstItem()
                mControlsVisible = true
            }else{
                onFirstItem()
            }

        } else {
            if (mScrolledDistance > HIDE_THRESHOLD && mControlsVisible) {
                Log.e(
                    "HidingScrollListener",
                    "mScrolledDistance > HIDE_THRESHOLD && mControlsVisible"
                )
                onHide()
                mControlsVisible = false
                mScrolledDistance = 0
            } else if (mScrolledDistance < -1000 && !mControlsVisible) {
                Log.e("HidingScrollListener", "mScrolledDistance < -1000 && !mControlsVisible")
                onShow()
                mControlsVisible = true
                mScrolledDistance = 0
            }
        }
        if (mControlsVisible && dy > 0 || !mControlsVisible && dy < 0) {
            mScrolledDistance += dy
//            Log.e("HidingScrollListener","dy "+dy)
        }
    }

    abstract fun onHide()
    abstract fun onShow()
    abstract fun onFirstItem()

    companion object {
        private const val HIDE_THRESHOLD = 20
    }
}
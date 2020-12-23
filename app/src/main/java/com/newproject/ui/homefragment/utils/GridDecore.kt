package com.newproject.ui.homefragment.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridDecore: RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
//        outRect.left = 12
//        outRect.right = 12
//        outRect.top = 20
        outRect.bottom = 10
    }
}
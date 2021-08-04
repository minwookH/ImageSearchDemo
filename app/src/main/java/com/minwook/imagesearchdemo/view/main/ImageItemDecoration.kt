package com.minwook.imagesearchdemo.view.main

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ImageItemDecoration(private val paddingSize: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val index = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex
        val position = parent.getChildLayoutPosition(view)

        //상하 설정
        if (position == 0 || position == 1 || position == 2) {
            // 첫번 째 줄 아이템
            outRect.top = paddingSize
            outRect.bottom = paddingSize
        } else {
            outRect.bottom = paddingSize
        }

        // spanIndex = 0 -> 왼쪽
        // spanIndex = 1 -> 오른쪽
        val layoutParam = view.layoutParams as GridLayoutManager.LayoutParams
        val spanIndex = layoutParam.spanIndex

        Log.d("testtest", "position : $position , spanIndex : $spanIndex")
        if (spanIndex == 0) {
            //왼쪽 아이템
            outRect.left = paddingSize
            outRect.right = paddingSize / 2
        } else if (spanIndex == 1) {
            //오른쪽 아이템
            outRect.left = paddingSize / 2
            outRect.right = paddingSize / 2
        } else {
            //오른쪽 아이템
            outRect.left = paddingSize / 2
            outRect.right = paddingSize
        }
    }
}
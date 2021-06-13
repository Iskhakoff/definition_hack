package ru.iskhakoff.rarible_app.adapter.decoration

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class ProjectsItemsDecoration(private val dividerDrawable: Drawable, private val spacing : Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        with(outRect){
            top = spacing
            left = spacing
            right = spacing
            bottom = spacing + dividerDrawable.intrinsicHeight
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.children.forEach { view ->
            val left = view.left - spacing
            val top = view.bottom + spacing
            val right = view.right + spacing
            val bottom = top + dividerDrawable.intrinsicHeight

            dividerDrawable.bounds = Rect(left, top, right, bottom)
            dividerDrawable.draw(c)
        }
    }
}
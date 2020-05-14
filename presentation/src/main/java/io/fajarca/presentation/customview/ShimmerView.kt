package io.fajarca.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.*
import androidx.annotation.LayoutRes
import com.facebook.shimmer.ShimmerFrameLayout
import io.fajarca.presentation.R
import io.fajarca.presentation.extension.gone
import io.fajarca.presentation.extension.visible

class ShimmerView : ShimmerFrameLayout {


    private lateinit var container : LinearLayout

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }


    private fun init(context: Context) {
        val view = View.inflate(context, R.layout.shimmer_placeholder, this)
        container = view.findViewById(R.id.container) as LinearLayout


        for (x in 0..5) {
            val placeholder = inflate(context, R.layout.default_placeholder, null )
            container.addView(placeholder)
        }

    }

    fun start() {
        visible()
        startShimmer()
    }

    fun start(context: Context, numberOfPlaceholderItem : Int) {
        container.removeAllViews()
        repeat(numberOfPlaceholderItem) {
            val placeholder = inflate(context, R.layout.default_placeholder, null )
            container.addView(placeholder)
        }
        visible()
        startShimmer()
        invalidate()
    }

    fun start(context: Context, numberOfPlaceholderItem : Int, @LayoutRes layoutResId : Int) {
        container.removeAllViews()
        repeat(numberOfPlaceholderItem) {
            val item = inflate(context, layoutResId, null )
            container.addView(item)
        }
        visible()
        startShimmer()
        invalidate()
    }

    fun stop() {
        gone()
        stopShimmer()
    }

}
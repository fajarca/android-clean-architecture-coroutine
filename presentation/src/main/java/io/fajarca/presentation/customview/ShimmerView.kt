package io.fajarca.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import com.facebook.shimmer.ShimmerFrameLayout
import io.fajarca.presentation.R
import io.fajarca.presentation.extension.gone
import io.fajarca.presentation.extension.visible


class ShimmerView : ShimmerFrameLayout {

    companion object {
        const val DEFAULT_PLACEHOLDER_ITEM_COUNT = 5
    }

    private lateinit var container : LinearLayout

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
        initAttributeSet(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
        initAttributeSet(attrs)
    }


    private fun init(context: Context) {
        val view = View.inflate(context, R.layout.shimmer_placeholder, this)
        container = view.findViewById(R.id.container) as LinearLayout
    }

    private fun initAttributeSet(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShimmerView)
        val placeHolderItemCount = typedArray.getInt(R.styleable.ShimmerView_sv_placeholder_item_count, DEFAULT_PLACEHOLDER_ITEM_COUNT)
        start(placeHolderItemCount)
        typedArray.recycle()
    }


    fun start(numberOfPlaceholderItem : Int) {
        createPlaceholderItem(numberOfPlaceholderItem)
        visible()
        startShimmer()
        invalidate()
    }

    fun start(numberOfPlaceholderItem : Int, @LayoutRes layoutResId : Int) {
        createPlaceholderItem(numberOfPlaceholderItem, layoutResId)
        visible()
        startShimmer()
        invalidate()
    }

    fun stop() {
        gone()
        stopShimmer()
    }

    private fun createPlaceholderItem(numberOfPlaceholderItem : Int, layoutResId: Int = R.layout.default_placeholder) {
        repeat(numberOfPlaceholderItem) {
            val placeholder = inflate(context, layoutResId, null )
            container.addView(placeholder)
        }
        invalidate()
    }

}
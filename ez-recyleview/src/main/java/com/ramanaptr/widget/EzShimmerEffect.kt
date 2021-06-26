package com.ramanaptr.widget

import android.content.Context
import android.util.AttributeSet
import com.facebook.shimmer.ShimmerFrameLayout

/**
 * EzShimmerEffect
 * Created by ramanaptr
 * ShimmerFrameLayout created by Facebook
 */
class EzShimmerEffect : ShimmerFrameLayout {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    override fun startShimmer() {
        super.startShimmer()
        this.visibility = VISIBLE
    }

    override fun hideShimmer() {
        super.hideShimmer()
        this.visibility = GONE
    }
}
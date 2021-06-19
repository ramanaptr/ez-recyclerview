package com.ramanaptr.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import com.facebook.shimmer.ShimmerFrameLayout
import java.io.Serializable

/**
 * EzShimmerEffect
 * Created by ramanaptr
 * ShimmerFrameLayout created by Facebook
 */
class EzShimmerEffect<Data : Serializable?> : ShimmerFrameLayout {
    private var shimmerViewId: Int = R.id.sfl
    private var recycleView: EzRecyclerView<Data>? = null

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    fun startShimmer(@NonNull recycleView: EzRecyclerView<Data>?, @NonNull counts: Int, @NonNull @LayoutRes layout: Int) {
        this.recycleView = recycleView
        this.recycleView?.setViewHolderLayout(layout) { view: View, data: Data -> bindViewHolder(view, data) }
        initData(counts)
        super.showShimmer(true)
        super.startShimmer()
    }

    fun startShimmer(@NonNull recycleView: EzRecyclerView<Data>?, counts: Int, @NonNull @LayoutRes layout: Int, @NonNull shimmerViewId: Int) {
        this.shimmerViewId = when (shimmerViewId) {
            0 -> R.id.sfl
            else -> shimmerViewId
        }

        this.recycleView = recycleView
        this.recycleView?.setViewHolderLayout(layout) { view: View, data: Data -> bindViewHolder(view, data) }
        initData(counts)
        super.showShimmer(true)
        super.startShimmer()
    }

    private fun initData(counts: Int) {
        for (i in 0 until counts) {
            val dummyValue = "Cast the data" as Data
            recycleView?.add(dummyValue)
        }
    }

    private fun bindViewHolder(view: View, data: Data) {
        val shimmerFrameLayout: ShimmerFrameLayout = view.findViewById(shimmerViewId)
        shimmerFrameLayout.startShimmer()
    }

    fun preHideShimmer() {
        recycleView?.removeAll()
    }

    override fun hideShimmer() {
        super.hideShimmer()
        this.visibility = GONE
    }
}
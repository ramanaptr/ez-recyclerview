package com.ramanaptr.widget.model;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import com.ramanaptr.widget.R;

public class EzMultipleLayout {

    private int customShimmerLayout = R.layout.default_shimmer_ez_recyclerview;
    private int shimmerViewId = R.id.sfl;
    private int layoutAdUnit;
    private int layoutLoading;
    private int layout1;
    private int layout2;
    private int layout3;
    private int layout4;
    private int layout5;
    private int layout6;
    private int layout7;

    public int getLayoutAdUnit() {
        return layoutAdUnit;
    }

    public void setLayoutAdUnit(@LayoutRes int layoutAdUnit) {
        this.layoutAdUnit = layoutAdUnit;
    }

    public int getCustomShimmerLayout() {
        return customShimmerLayout;
    }

    public int getShimmerViewId() {
        return shimmerViewId;
    }

    public void setCustomShimmerLayout(@LayoutRes int layoutShimmer, @IdRes int shimmerViewId) {
        this.customShimmerLayout = layoutShimmer;
        this.shimmerViewId = shimmerViewId;
    }

    public int getLayoutLoading() {
        return layoutLoading;
    }

    public void setLayoutLoading(@LayoutRes int layoutLoading) {
        this.layoutLoading = layoutLoading;
    }

    public int getLayout1() {
        return layout1;
    }

    public void setLayout1(@LayoutRes int layout1) {
        this.layout1 = layout1;
    }

    public int getLayout2() {
        return layout2;
    }

    public void setLayout2(@LayoutRes int layout2) {
        this.layout2 = layout2;
    }

    public int getLayout3() {
        return layout3;
    }

    public void setLayout3(@LayoutRes int layout3) {
        this.layout3 = layout3;
    }

    public int getLayout4() {
        return layout4;
    }

    public void setLayout4(@LayoutRes int layout4) {
        this.layout4 = layout4;
    }

    public int getLayout5() {
        return layout5;
    }

    public void setLayout5(@LayoutRes int layout5) {
        this.layout5 = layout5;
    }

    public int getLayout6() {
        return layout6;
    }

    public void setLayout6(int layout6) {
        this.layout6 = layout6;
    }

    public int getLayout7() {
        return layout7;
    }

    public void setLayout7(int layout7) {
        this.layout7 = layout7;
    }
}

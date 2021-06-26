package com.ramanaptr.widget.model;

import com.ramanaptr.widget.constant.EzViewType;

import java.io.Serializable;

public class EzBaseData implements Serializable {

    private EzViewType ezViewType = EzViewType.LAYOUT_1;

    public EzViewType getEzViewType() {
        return ezViewType;
    }

    public void setEzViewType(EzViewType ezViewType) {
        this.ezViewType = ezViewType;
    }

    public boolean isLoadingLayout() {
        return ezViewType == EzViewType.LOADING;
    }

    public boolean isCustomShimmerLayout() {
        return ezViewType == EzViewType.SHIMMER_EFFECT;
    }

    public boolean isAdUnitLayout() {
        return ezViewType == EzViewType.AD_UNIT;
    }

    public boolean isLayout1() {
        return ezViewType == EzViewType.LAYOUT_1;
    }

    public boolean isLayout2() {
        return ezViewType == EzViewType.LAYOUT_2;
    }

    public boolean isLayout3() {
        return ezViewType == EzViewType.LAYOUT_3;
    }

    public boolean isLayout4() {
        return ezViewType == EzViewType.LAYOUT_4;
    }

    public boolean isLayout5() {
        return ezViewType == EzViewType.LAYOUT_5;
    }

    public boolean isLayout6() {
        return ezViewType == EzViewType.LAYOUT_6;
    }

    public boolean isLayout7() {
        return ezViewType == EzViewType.LAYOUT_7;
    }
}

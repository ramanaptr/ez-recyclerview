package com.ramanaptr.widget.constant;

public enum EzViewType {
    SHIMMER_EFFECT(1),
    AD_UNIT(2),
    LOADING(3),
    LAYOUT_1(4),
    LAYOUT_2(5),
    LAYOUT_3(6),
    LAYOUT_4(7),
    LAYOUT_5(8),
    LAYOUT_6(9),
    LAYOUT_7(10),
    ;

    private final int type;

    EzViewType(int type) {
        this.type = type;
    }

    public static EzViewType findByViewType(int viewType) {
        for (EzViewType value : values()) {
            if (value.getViewType() == viewType) {
                return value;
            }
        }
        return EzViewType.LAYOUT_1;
    }

    public int getViewType() {
        return type;
    }
}

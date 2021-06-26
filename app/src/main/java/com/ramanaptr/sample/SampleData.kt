package com.ramanaptr.sample

import android.os.Parcelable
import com.ramanaptr.widget.model.EzBaseData
import kotlinx.parcelize.Parcelize

@Parcelize
data class SampleData(
    var key: String? = null,
    var value: String? = null,
) : EzBaseData(), Parcelable

# Ez Recycler View

[![](https://jitpack.io/v/ramanaptr/ez-recyclerview.svg)](https://jitpack.io/#ramanaptr/ez-recyclerview)


Add maven jitpack.io into build.gradle (classpath repositories)
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
     }
}
```


Add implementation Dependency into build.gradle
```
dependencies {
  implementation 'com.github.ramanaptr:ez-recyclerview:<latest-version>'
}
```

XML:
```
<com.ramanaptr.widget.EzRecyclerView
  android:id="@+id/rv_sample"
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"/>
```

Example For the Data
```
// You must do implement Serializable every create a data class and want to transaction with ez recylerview
data class SampleData(
    var key: String,
    var value: String,
) : Serializable
```

Example Kotlin Code
```
// For Generic Type on field/declare properties on the class, the class data must implement Serializable
// Example data class: SampleData
private lateinit var rvSample: EzRecyclerView<SampleData>

// Use findViewById or Butterknife, because not working on kotlin synthetic
rvSample = findViewById(R.id.rv_sample)

// 10 is the amount/size of shimmer view to showing on loading
rvSample.startShimmer(10)

// Much overloading function, this function you can use every time when you want to load the data
rvSample.setViewHolderLayout(R.layout.sample_view_holder, dataList, bindViewHolder)

// This is for bind the view holder and data object
private val bindViewHolder = { view: View, data: SampleData ->
  val tvKey = view.findViewById<TextView>(R.id.tv_key)
  val tvValue = view.findViewById<TextView>(R.id.tv_value)
  tvKey.text = data.key
  tvValue.text = data.value
}
```
That's it.


Example Kotlin Code for Multiple View
```
// Right now Ez RecyclerView handling only 2 layouts
val layouts =  IntArray(2) {R.layout.sample_view_holder_1; R.layout.sample_view_holder_2}
rvSample.setViewHolderLayout(layouts, bindViewHolder)

// Handling for the data 1 and 2 using safety condition
private val bindViewHolder = { view: View, data: SampleData ->
        val tvKey1 = view.findViewById<TextView>(R.id.tv_key1)
        val tvValue1 = view.findViewById<TextView>(R.id.tv_value1)
        val tvKey2 = view.findViewById<TextView>(R.id.tv_key2)
        val tvValue2 = view.findViewById<TextView>(R.id.tv_value2)
        if (tvKey1 != null) {
            tvKey1.text = data.key1
            tvValue1.text = data.value1
        } else if(tvKey2 != null) {
            tvKey2.text = data.key2
            tvValue2.text = data.value2
        }
    }
```

Example Adding/Replace/Remove data also refresh the data
```
rvSample.addAll(dataList) // Add all without another views position
rvSample.add(data) // Add without remove another view position
rvSample.replaceAll(dataList ) // Replace All views position
rvSample.replace(data) // Replace a view position
rvSample.removeAll() // Remove all views and data
rvSample.remove(data) // Remove view and data
rvSample.refresh() // Refresh when data not change yet
```

Implement the LayoutManager programmatically
```
rvSample.setDefaultLayoutManager() // Default layout manager
rvSample.setGridLayoutManager(3) // Default grid layout with param span
rvSample.setFlexBoxLayoutManager() // Default Flexbox layout manager
rvSample.setFlexBoxLayoutManager(FlexDirection.ROW) // Flexbox layout manager with param FlexDirection interface
rvSample.setHorizontalLinearLayoutManager() // Default horizontal linear layout manager

// or custom by you
rvSample.setLayoutManager(); // Ez Recylerview allow you to custom your own layout manager
```

Example XML Custom Layout Shimmer
```
<?xml version="1.0" encoding="utf-8"?>
<!-- Give an id of EzShimmerEffect, example: like this "@+id/sample_shimmer"-->
<com.ramanaptr.widget.EzShimmerEffect xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/sample_shimmer"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginBottom="8dp"
    android:padding="2dp">

    <androidx.appcompat.widget.LinearLayoutCompat
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="30dp"
            android:layout_marginStart="12dp"
            android:layout_weight="1"
            android:background="@color/grey" />

    </androidx.appcompat.widget.LinearLayoutCompat>

</com.ramanaptr.widget.EzShimmerEffect>
```

Example Implementation Custom Shimmer to binding the view id
```
// implement view id R.id.sample_shimmer mean of shimmer frame layout into param
rvSample.startShimmer(10, R.layout.sample_shimmer_effect, R.id.sample_shimmer)
```

Hide Shimmer on Loading
```
rvSample.hideShimmer()
```

You won't use the shimmer effect?
```
// don't use this function every running
rvSample.startShimmer()
```

Happy Coding, make it simple, fast, and efficient!! 💪

Note: 
Are you want to contribute to this project? 
Very welcome, any question DM me on my website or report the issue, I'll try my best to fix it and maintain this project or answer your questions 😁

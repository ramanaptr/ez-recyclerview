# Ez Recycler View

[![](https://jitpack.io/v/ramanaptr/ez-recyclerview.svg)](https://jitpack.io/#ramanaptr/ez-recyclerview)


Adding Depedency build.gradle (classpath repositories)
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
     }
}
```


Adding Depedency on build.gralde
```
dependencies {
  implementation 'com.github.ramanaptr:ez-recyclerview:<latest>’
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

Example Kotlin Code:
```
// For Generic Type on field/declare properties on the class
private lateinit var rvSample: EzRecyclerView<SampleData>

// use findViewById or Butterknife, because not working on kotlin synthetic
rvSample = findViewById(R.id.rv_sample)

// 10 is the amount/size of shimmer view to showing
rvSample.startShimmer(10)

// Much overloading function, this function you can use every time when you want to load the data
rvSample.setViewHolderLayout(R.layout.sample_view_holder, dataList, bindViewHolder)

// This is for bind the view holder and data object.
private val bindViewHolder = { view: View, data: SampleData ->
  val tvKey = view.findViewById<TextView>(R.id.tv_key)
  val tvValue = view.findViewById<TextView>(R.id.tv_value)
  tvKey.text = data.key
  tvValue.text = data.value
}
```

Example Kotlin Code for Multiple View
```
// Right now Ez RecyclerView handling only 2 layouts
val layouts =  IntArray(2) {R.layout.sample_view_holder_1; R.layout.sample_view_holder_2}
rvSample.setViewHolderLayout(layouts, bindViewHolder)

// Handling for the data 1 and 2 using condition
private val bindViewHolder = { view: View, data: SampleData ->
        if (data.key2.isEmpty()) {
            val tvKey1 = view.findViewById<TextView>(R.id.tv_key1)
            val tvValue1 = view.findViewById<TextView>(R.id.tv_value1)
            tvKey1.text = data.key1
            tvValue1.text = data.value1
        } else {
            val tvKey2 = view.findViewById<TextView>(R.id.tv_key2)
            val tvValue2 = view.findViewById<TextView>(R.id.tv_value2)
            tvKey2.text = data.key2
            tvValue2.text = data.value2
        }
    }
```

Don't like shimmer effect?
```
// Don't use this function every running
rvSample.startShimmer(10)
```

Happy Coding, make it simple, fast and efficiency!!

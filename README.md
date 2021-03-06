# Ez Recycler View

## Preview and Introduction
<p align="center">
  <img  src="https://github.com/ramanaptr/ez-recyclerview/blob/main/app/src/main/res/raw/ez_recyclerview_preview.gif?raw=true" width="210" height="360"/> 
</p>
<p align="center">
  <a href="https://jitpack.io/#com.ramanaputra/ez-recyclerview">
    <img src="https://jitpack.io/v/com.ramanaputra/ez-recyclerview.svg" alt="jitpack - ez recyclerview" />
  </a>
  <img src="https://visitor-badge.laobi.icu/badge?page_id=ramanaptr.ez-recyclerview" alt="visitor - ez recyclerview" />
  <br>
  <br>
  <p>Ez-RecyclerView is a library that helps the android developer to make it easier when to create a basic recycler view. To create the recycler view I'm sure another developer to always repeat it, especially the ViewHolder and its Adapter, so I decided to improve and make it simple, "Ez-RecyclerView" library the main advantage, you can display a list of data without creating View Holders and Adapters repeatedly.. If you are interested, let's go contribute with me 👍 </p>
</p>


## Setup

Add this to your project's `build.gradle`
```gradle
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```


Add implementation Dependency into your module's `build.gradle`
```gradle
dependencies {
  implementation 'com.ramanaputra:ez-recyclerview:<latest-version>'
}
```

XML:
```xml
<com.ramanaptr.widget.EzRecyclerView
  android:id="@+id/rv_sample"
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"/>
```

Example For the Data with EzBaseData
```kotlin
data class SampleData(
    var key: String? = null,
    var value: String? = null,
) : EzBaseData()
```

## Example Single Layout
```kotlin
/**
 * You must extend the object "EzBaseData".
 * Because the object "EzBaseData" job for managing multiple layout
 * */
private lateinit var rvSample: EzRecyclerView<SampleData>

private fun exampleEzRecycleSingleLayout() {

    // if you want use findViewById() you shouldn't cast the EzRecycleView
    // if you want use view binding you should to cast the object like the example below
    // If you won't casting the object, you can use #EzRecyclerView.bind()
    rvSample = EzRecyclerView.bind(binding.rvSample)
    
    // set class data
    rvSample.setData(SampleData::class.java)

    // example function for pagination on Ez-RecyclerView
    // init the pagination after bind the view and declare it into field
    initPaginationEzRecyclerView()

    // set your view holder layout
    rvSample.setViewHolderLayout(R.layout.sample_view_holder_layout_one) { view: View, data: SampleData ->

        // handle view by layout and check value of the data null
        // please do take a note for your data, always to check value inside your object to avoid null exception
        // example in the below
        view.findViewById<TextView>(R.id.tv_key_one).apply { data.key?.apply { text = this } }
        view.findViewById<TextView>(R.id.tv_value_one).apply { data.value?.apply { text = this } }

    }
}

private fun exampleDataForSingleLayout(size: Int) {
    // start shimmer when load the data
    // please to use your empty object like SampleData()
    // rvSample.startShimmer(size, SampleData()) // @Deprecated
    rvSample.startShimmer(size)

    // start load the data
    // populate the data into Ez-RecyclerView
    rvSample.addAll(dataList)

    // hide the shimmer after the data showing
    rvSample.hideShimmer()
}
```
That's it.

Alternative for bind the view id
```kotlin
// example with local variable when using view binding
val rvSample = EzRecyclerView.bind<SampleData>(binding.rvSample)

// example with local variable when using view binding but there is a raw cast warning
val rvSample = binding.rvSample as EzRecyclerView<SampleData>

// example with local variable when using findViewById()
val rvSample = findViewById<EzRecyclerView<SampleData>>(R.id.rv_sample)
```
and choose according to your convenience


## Example Multiple View
```kotlin
private fun exampleEzRecycleMultipleLayout() {

    // if you want use findViewById() you shouldn't cast the EzRecycleView
    // if you want use view binding you should to cast the object like the example below
    // If you won't casting the object, you can use #EzRecyclerView.bind()
    rvSample = EzRecyclerView.bind(binding.rvSample)
    
    // set class data
    rvSample.setData(SampleData::class.java)

    // example function for pagination on Ez-RecyclerView
    // init the pagination after bind the view and declare it into field
    initPaginationEzRecyclerView()

    // set your view holder layout
    rvSample.setLayout1(R.layout.sample_view_holder_layout_one)
    rvSample.setLayout2(R.layout.sample_view_holder_layout_two)
    
    // set you custom shimmer layout
    rvSample.setCustomShimmerLayout(R.layout.sample_custom_shimmer_effect)

    // you can use "setViewHolderLayout" directly without "EzMultipleLayout" object
    rvSample.setViewHolderLayout { view: View, data: SampleData ->

        // handle view by layout and check value of the data null
        // please do take a note for your data, always to check value inside your object to avoid null exception
        // example in the below
        when {
            data.isLayout1 -> {
                view.findViewById<TextView>(R.id.tv_key_one)
                    .apply { data.key?.apply { text = this } }
                view.findViewById<TextView>(R.id.tv_value_one)
                    .apply { data.value?.apply { text = this } }
            }
            data.isLayout2 -> {
                view.findViewById<TextView>(R.id.tv_key_two)
                    .apply { data.key?.apply { text = this } }
                view.findViewById<TextView>(R.id.tv_value_two)
                    .apply { data.value?.apply { text = this } }
            }
        }

    }
}

private fun exampleHandlingDataMultipleLayout(size: Int) {

  // populate the data list
  val dataList = arrayListOf<SampleData>().apply {
    for (i in 1..size) {

      // example handling data using ezViewType in layout 2
      // you must implement ezViewType because Ez-Recycler must to know attach the data into view you assign
      // handling every layout is manually with your own condition for example "isLayoutTwo", after that set for the ezViewType using "EzViewType"
      val isLayoutTwo = i % 3 == 0 // example for display layout data 2 every 3 times
      if (isLayoutTwo) {
          val sampleDataTwo = SampleData("- Sub Key $i", "Sub Value $i")
          sampleDataTwo.ezViewType = EzViewType.LAYOUT_2
          add(sampleDataTwo)
          continue
      }

      // example handling data using ezViewType in layout 1
      // you must implement ezViewType because Ez-Recycler must to know attach the data into view you assign
      // after that set for the ezViewType using "EzViewType"
      val sampleDataOne = SampleData("Key $i", "Value $i")
      sampleDataOne.ezViewType = EzViewType.LAYOUT_1
      add(sampleDataOne)
    }
  }
}
```
And, That's it.

Another method for implementation multiple view
```kotlin
// create a new object of "EzMultipleLayout" and set your view holder layout into the object
val ezMultipleLayout = EzMultipleLayout()
ezMultipleLayout.layout1 = R.layout.sample_view_holder_layout_one
ezMultipleLayout.layout2 = R.layout.sample_view_holder_layout_two

// set your custom shimmer layout
ezMultipleLayout.setCustomShimmerLayout(R.layout.sample_custom_shimmer_effect)

// store "ezMultipleLayout" into param of "setViewHolderLayout()" and implement callback/listener in bindViewHolder
rvSample.setViewHolderLayout(ezMultipleLayout) { view: View, data: SampleData -> {} }
```

## Pagination Example
```kotlin
/**
 * if you like using pagination, this method match for you.
 * "setEzPaginationListener()" for the paging your page from local DB, remote data such as API or others
 * You can setting with your own "limit", "offset"
 * "currentPage" is automatically increment for the next page after you scrolling down or scrolling horizontal
 * */

 // 5 is mean initial limit, and 0 is mean initial offset
 // on "newOffset" mean next offset, and on "currentPage" next of the page
private fun initPaginationEzRecyclerView() {
    rvSample.setEzPaginationListener(5, 0) { limit: Int, newOffset: Int, currentPage: Int ->
        exampleDataForSingleLayout(newOffset)
    }
}
```

## Adding/Replace/Remove/Refresh/Destroy/Reset
```kotlin
rvSample.addAll(dataList) // Add all data without remove/replace another views position
rvSample.add(data) // Add data without remove/replace another view position
rvSample.replaceAll(dataList) // Replace All the same views position (data must be the same)
rvSample.replace(data) // Replace a same view position (data must be the same)
rvSample.removeAll() // Remove all views and data
rvSample.remove(data) // Remove view and data
rvSample.refresh() // Refresh when data not change yet
rvSample.destroy() // Destroy the object on field and reset the value
rvSample.reset() // Reset all state on recyclerview such view, first initial pagination value, and data list
```

## LayoutManager Programmatically
```kotlin
rvSample.setDefaultLayoutManager() // Default layout manager
rvSample.setGridLayoutManager(3) // Default grid layout with param span
rvSample.setFlexBoxLayoutManager() // Default Flexbox layout manager
rvSample.setFlexBoxLayoutManager(FlexDirection.ROW) // Flexbox layout manager with param FlexDirection interface
rvSample.setHorizontalLinearLayoutManager() // Default horizontal linear layout manager
```

## Example XML Custom Layout Shimmer
```xml
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
```kotlin
// your shimmer effect layout
rvSample.setCustomShimmerLayout(R.layout.sample_custom_shimmer_effect)
  
// you can use "setViewHolderLayout" directly without "EzMultipleLayout" object
rvSample.setViewHolderLayout { view: View, data: SampleData -> {} }
```

Another Example Implementation Custom Shimmer to binding the view id
```kotlin
// you can use "setViewHolderLayout" with "EzMultipleLayout" object
ezMultipleLayout.setCustomShimmerLayout(R.layout.sample_custom_shimmer_effect)

// store "ezMultipleLayout" into param of "setViewHolderLayout()" and implement callback bindViewHolder
rvSample.setViewHolderLayout(ezMultipleLayout) { view: View, data: SampleData -> {} }
```

Start Shimmer on loading the data
```kotlin
// 10 is the size view on shimmer effect, and "SampleData" for empty object from EzRecyclerView<SampleData>
// please do take a note for "Sample Data" is extend/inheritance from "Ez BaseData"
rvSample.startShimmer(size, SampleData()) // Deprecated
rvSample.startShimmer(size) // Recommended 
```

Hide Shimmer on complete showing the data
```kotlin
rvSample.hideShimmer()
```

You won't use the shimmer effect?
```kotlin
// don't use this function every running
rvSample.startShimmer()
```

`Important!` Always call function destroy to avoid memory leak or error state
```kotlin
override fun onDestroy() {
  super.onDestroy()
  rvSample.destroy()
}
```

`Important!` Recommend for use this function when you re-start or resume again the data
```kotlin
// please to use
rvSample.reset()

// example on refresh the data again
swipeRefreshLayout.setOnRefreshListener {
    binding.srl.isRefreshing = false
    rvSample.reset()
    // TODO: your logic data
}
```

Happy Coding, make it simple, fast, and efficient!! 💪

>Note: Are you want to contribute to this project? Very welcome, any question DM me on my website or report the issue, I'll try my best to fix it and maintain this project or answer your questions 😁

## Inspired
https://github.com/cs-app

### License
```
Copyright 2021 Ramana Putra

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

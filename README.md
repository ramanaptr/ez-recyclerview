# Ez Recycler View

[![](https://jitpack.io/v/ramanaptr/ez-recyclerview.svg)](https://jitpack.io/#ramanaptr/ez-recyclerview) ![GitHub all releases](https://img.shields.io/github/downloads/ramanaptr/ez-recyclerview/total) ![visitors](https://visitor-badge.laobi.icu/badge?page_id=ramanaptr.ez-recyclerview)

Add maven jitpack.io into build.gradle (classpath repositories)
```gradle
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```


Add implementation Dependency into build.gradle
```gradle
dependencies {
  implementation 'com.github.ramanaptr:ez-recyclerview:<latest-version>'
}
```

## Setup

XML:
```xml
<com.ramanaptr.widget.EzRecyclerView
  android:id="@+id/rv_sample"
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"/>
```

Example For the Data
```kotlin
data class SampleData(
    var key: String? = null,
    var value: String? = null,
) : EzBaseData()
```

Example Kotlin Code for Single Layout
```kotlin
    /**
     * You must extend the object EzBaseData.
     * Because method for managing multiple layout
     * */
    private lateinit var rvSample: EzRecyclerView<SampleData>
    private fun exampleEzRecycleSingleLayout() {

        // if you want use findViewById() you'll shouldn't cast the EzRecycleView
        // if you want use view binding you should to cast the object like the example below
        rvSample = binding.rvSample as EzRecyclerView<SampleData>

        // example function for pagination on Ez-RecyclerView
        // init the pagination after bind the view and declare it into field
        initPaginationEzRecyclerView()

        // set your view holder layout
        rvSample.setViewHolderLayout(R.layout.sample_view_holder_layout_one) { view: View, data: SampleData ->

            // handle view by layout and check value of the data null
            // please take a note for your data, always to check value inside your object to avoid null exception
            // example in the below
            view.findViewById<TextView>(R.id.tv_key_one).apply { data.key?.apply { text = this } }
            view.findViewById<TextView>(R.id.tv_value_one).apply { data.key?.apply { text = this } }

        }
    }
    
    private fun exampleDataForSingleLayout(size: Int) {
        // start shimmer when load the data
        // please to use your empty object like SampleData()
        rvSample.startShimmer(size, SampleData())

        // start load the data
        // populate the data into Ez-RecyclerView
        rvSample.addAll(dataList)

        // hide the shimmer after the data showing
        rvSample.hideShimmer()
    }
```
That's it.


Example Kotlin Code for Multiple View
```kotlin
    private fun exampleEzRecycleMultipleLayout() {

        // if you want use findViewById() you'll shouldn't cast the EzRecycleView
        // if you want use view binding you should to cast the object like the example below
        rvSample = binding.rvSample as EzRecyclerView<SampleData>

        // example function for pagination on Ez-RecyclerView
        // init the pagination after bind the view and declare it into field
        initPaginationEzRecyclerView()

        // set the layout before "setViewHolderLayout()"
        rvSample.setLayout1(R.layout.sample_view_holder_layout_one)
        rvSample.setLayout2(R.layout.sample_view_holder_layout_two)

        // set custom shimmer effect after that, bind the shimmer view layout by R.id.<shimmer_view_id> from R.layout.<your_shimmer_layout>
        // and set into "ezMultipleLayout" with method "setCustomShimmerLayout()"
        rvSample.setCustomShimmerLayout(
            R.layout.sample_custom_shimmer_effect,
            R.id.sample_shimmer_view_id
        )

        // you can use "setViewHolderLayout" directly without "EzMultipleLayout" object
        rvSample.setViewHolderLayout { view: View, data: SampleData ->

            // handle view by layout and check value of the data null
            // please take a note for your data, always to check value inside your object to avoid null exception
            // example in the below
            when {
                data.isLayout1 -> {
                    view.findViewById<TextView>(R.id.tv_key_one)
                        .apply { data.key?.apply { text = this } }
                    view.findViewById<TextView>(R.id.tv_value_one)
                        .apply { data.key?.apply { text = this } }
                }
                data.isLayout2 -> {
                    view.findViewById<TextView>(R.id.tv_key_two)
                        .apply { data.key?.apply { text = this } }
                    view.findViewById<TextView>(R.id.tv_value_two)
                        .apply { data.key?.apply { text = this } }
                }
            }

        }
    }
```
And, That's it.

Another method for implemenation multiple view
```kotlin
  // create a new object of "EzMultipleLayout" and set your view holder layout into the object
  val ezMultipleLayout = EzMultipleLayout()
  ezMultipleLayout.layout1 = R.layout.sample_view_holder_layout_one
  ezMultipleLayout.layout2 = R.layout.sample_view_holder_layout_two

  // custom shimmer effect after that, bind the shimmer view layout by R.id.<shimmer_view_id> from R.layout.<your_shimmer_layout>
  // and set into "ezMultipleLayout" with method "setCustomShimmerLayout()"
  ezMultipleLayout.setCustomShimmerLayout(
      R.layout.sample_custom_shimmer_effect,
      R.id.sample_shimmer_view_id
  )

  // store "ezMultipleLayout" into param of "setViewHolderLayout()" and implement callback bindViewHolder
  rvSample.setViewHolderLayout(ezMultipleLayout) { view: View, data: SampleData -> {} }
```

## Example for implemenation pagination
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

## Adding/Replace/Remove data also refresh the data
```kotlin
rvSample.addAll(dataList) // Add all data without remove/replace another views position
rvSample.add(data) // Add data without remove/replace another view position
rvSample.replaceAll(dataList) // Replace All the same views position (data must be the same)
rvSample.replace(data) // Replace a same view position (data must be the same)
rvSample.removeAll() // Remove all views and data
rvSample.remove(data) // Remove view and data
rvSample.refresh() // Refresh when data not change yet
rvSample.destroy() // Destroy the object on field and reset the value
```

## Implement the LayoutManager programmatically
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
// you can use "setViewHolderLayout" directly without "EzMultipleLayout" object
rvSample.setCustomShimmerLayout(
    R.layout.sample_custom_shimmer_effect,
    R.id.sample_shimmer_view_id
)

// store "ezMultipleLayout" into param of "setViewHolderLayout()" and implement callback bindViewHolder
rvSample.setViewHolderLayout(ezMultipleLayout) { view: View, data: SampleData -> {} }
```

Another Example Implementation Custom Shimmer to binding the view id
```kotlin
// you can use "setViewHolderLayout" directly without "EzMultipleLayout" object
  rvSample.setCustomShimmerLayout(
      R.layout.sample_custom_shimmer_effect,
      R.id.sample_shimmer_view_id
  )
```

Hide Shimmer on complete shoing the data
```kotlin
rvSample.hideShimmer()
```

You won't use the shimmer effect?
```kotlin
// don't use this function every running
rvSample.startShimmer()
```

Happy Coding, make it simple, fast, and efficient!! 💪

>Note: Are you want to contribute to this project? Very welcome, any question DM me on my website or report the issue, I'll try my best to fix it and maintain this project or answer your questions 😁

## Inspired
https://github.com/cs-app

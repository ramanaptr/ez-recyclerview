package com.ramanaptr.sample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ramanaptr.sample.databinding.ActivityMainBinding
import com.ramanaptr.widget.EzRecyclerView
import com.ramanaptr.widget.constant.EzViewType
import com.ramanaptr.widget.model.EzMultipleLayout
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var subscribe: Disposable? = null

    /**
     * You must extend the object EzBaseData.
     * Because method for managing multiple layout
     *  Example:
     *  data class SampleData( var key: String? = null, var value: String? = null) : EzBaseData()
     * */
    private lateinit var rvSample: EzRecyclerView<SampleData>

    /**
     * turn "isSingleLayout" into "false" when you want to try multi layout stuff
     * */
    private var isSingleLayout = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
        initializeEzRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        subscribe?.isDisposed?.apply { if (!this) subscribe?.dispose() }

        // this is for avoid memory leak and state error
        rvSample.destroy()
    }

    private fun initializeEzRecyclerView() {
        when {
            isSingleLayout -> {
                exampleEzRecycleSingleLayout()
            }
            else -> {
                exampleEzRecycleMultipleLayout()
            }
        }
    }

    /**
     * binding.srl.setOnRefreshListener
     * for refresh to adding new data
     * */
    private fun initListener() {
        binding.srl.setOnRefreshListener {
            binding.srl.isRefreshing = false
            when {
                isSingleLayout -> {
                    exampleDataForSingleLayout(15)
                }
                else -> {
                    exampleDataForMultipleLayout(15)
                }
            }

        }
    }

    /**
     * if you like using pagination, this method match for you.
     * "setEzPaginationListener()" for the paging your page from local DB, remote data such as API or others
     * You can setting with your own "limit", "offset"
     * "currentPage" is automatically increment for the next page after you scrolling down or scrolling horizontal
     * */
    private fun initPaginationEzRecyclerView() {
        rvSample.setEzPaginationListener(5, 0) { limit: Int, newOffset: Int, currentPage: Int ->
            when {
                isSingleLayout -> {
                    exampleDataForSingleLayout(newOffset)
                }
                else -> {
                    exampleDataForMultipleLayout(newOffset)
                }
            }
            Toast.makeText(this, "Page $currentPage and offset $newOffset", Toast.LENGTH_SHORT)
                .show()
        }
    }

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

        // this is just dummy data
        exampleDataForSingleLayout(5)
    }

    private fun exampleEzRecycleMultipleLayout() {

        // if you want use findViewById() you'll shouldn't cast the EzRecycleView
        // if you want use view binding you should to cast the object like the example below
        rvSample = binding.rvSample as EzRecyclerView<SampleData>

        // example function for pagination on Ez-RecyclerView
        // init the pagination after bind the view and declare it into field
        initPaginationEzRecyclerView()

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
        rvSample.setViewHolderLayout(ezMultipleLayout) { view: View, data: SampleData ->

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

        // this is just dummy data
        exampleDataForMultipleLayout(5)
    }

    private fun exampleDataForSingleLayout(size: Int) {
        // start shimmer when load the data
        // please to use your empty object like SampleData()
        rvSample.startShimmer(size, SampleData())

        // start load the data
        subscribe = Flowable.create<List<SampleData>>({
            Handler(Looper.getMainLooper()).postDelayed({

                // Example handling data in single layout
                val dataList = arrayListOf<SampleData>().apply {
                    for (i in 1..size) {
                        add(SampleData("Key $i", "Value $i"))
                    }
                }

                it.onNext(dataList)
                it.onComplete()
            }, 2000)
        }, BackpressureStrategy.BUFFER)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                // populate the data into Ez-RecyclerView shimmer
                rvSample.addAll(it)

            }, {
                it.printStackTrace()
            }, {
                Toast.makeText(this, "Completed!", Toast.LENGTH_SHORT).show()

                // hide the shimmer
                rvSample.hideShimmer()
            })
    }

    private fun exampleDataForMultipleLayout(size: Int) {
        // start shimmer when load the data
        // please to use your empty object like SampleData()
        rvSample.startShimmer(size, SampleData())

        // start load the data
        subscribe = Flowable.create<List<SampleData>>({
            Handler(Looper.getMainLooper()).postDelayed({

                // populate the data list
                val dataList = arrayListOf<SampleData>().apply {
                    for (i in 1..size) {

                        // example handling data using ezViewType in layout 2
                        // you must implement ezViewType because Ez-Recycler must to know attach the data into view you assign
                        if (i % 2 == 0) {
                            val sampleDataTwo = SampleData("- Sub Key $i", "Sub Value $i")
                            sampleDataTwo.ezViewType = EzViewType.LAYOUT_2
                            add(sampleDataTwo)
                            continue
                        }

                        // example handling data using ezViewType in layout 1
                        // you must implement ezViewType because Ez-Recycler must to know attach the data into view you assign
                        val sampleDataOne = SampleData("Key $i", "Value $i")
                        sampleDataOne.ezViewType = EzViewType.LAYOUT_1
                        add(sampleDataOne)
                    }
                }
                it.onNext(dataList)
                it.onComplete()
            }, 2000)
        }, BackpressureStrategy.BUFFER)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                // populate the data into Ez-RecyclerView shimmer
                rvSample.addAll(it)

            }, {
                it.printStackTrace()
            }, {
                Toast.makeText(this, "Completed!", Toast.LENGTH_SHORT).show()

                // hide the shimmer
                rvSample.hideShimmer()
            })
    }
}
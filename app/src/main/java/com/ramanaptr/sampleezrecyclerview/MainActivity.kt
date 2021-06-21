package com.ramanaptr.sampleezrecyclerview

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ramanaptr.widget.EzRecyclerView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private var subscribe: Disposable? = null
    private lateinit var rvSample: EzRecyclerView<SampleData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponent()
        initListener()
        // initDataWithCustomShimmer()
        // initDataWithDefaultShimmer() //TODO: Uncomment for test
        initDataWithRxJava()
    }

    override fun onDestroy() {
        super.onDestroy()
        subscribe?.isDisposed?.apply { if (this) subscribe?.dispose() }
    }

    private fun initComponent() {
        rvSample = findViewById(R.id.rv_sample)
    }

    private fun initListener() {
        btnRefresh.setOnClickListener {
            // initDataWithCustomShimmer()
            // initDataWithDefaultShimmer()
            initDataWithRxJava()
        }
        stopShimmer.setOnClickListener {
            rvSample.hideShimmer()
        }
    }

    /**
     * Please to implement view id R.id.sample_shimmer mean of shimmer frame layout into param
     * */
    private fun initDataWithCustomShimmer() {
        // Start Shimmer for waiting for the data
        rvSample.startShimmer(10, R.layout.sample_shimmer_effect, R.id.sample_shimmer)
        handler.postDelayed(showData, 5000)
    }

    private fun initDataWithDefaultShimmer() {
        // Start Shimmer for waiting for the data
        rvSample.startShimmer(10)
        handler.postDelayed(showData, 5000)
    }

    private fun initDataWithRxJava() {
        // start shimmer when load the data
        rvSample.startShimmer(10)
        // start load the data
        subscribe = Flowable.create<List<SampleData>>({
            val dataList = arrayListOf<SampleData>()
            Handler(Looper.getMainLooper()).postDelayed({
                for (i in 1..100000) {
                    dataList.add(SampleData("Key $i", "Value $i"))
                }
                it.onNext(dataList)
                it.onComplete()
            }, 2000)
        }, BackpressureStrategy.BUFFER)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                rvSample.setViewHolderLayout(R.layout.sample_view_holder, it, bindViewHolder)
            }, {
                it.printStackTrace()
            }, {
                Toast.makeText(this, "Completed!", Toast.LENGTH_SHORT).show()
            })
    }

    private val showData = {
        val dataList = arrayListOf<SampleData>()
        for (i in 1..100) {
            dataList.add(SampleData("Key $i", "Value $i"))
        }

        // Re set view Holder
        rvSample.setViewHolderLayout(R.layout.sample_view_holder, dataList, bindViewHolder)
    }

    private val bindViewHolder = { view: View, data: SampleData ->
        val tvKey = view.findViewById<TextView>(R.id.tv_key)
        val tvValue = view.findViewById<TextView>(R.id.tv_value)
        tvKey.text = data.key
        tvValue.text = data.value
    }
}
package com.ramanaptr.sampleezrecyclerview

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ramanaptr.widget.EzRecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var rvSample: EzRecyclerView<SampleData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponent()
        initListener()
        initDataWithCustomShimmer()
        // initDataWithDefaultShimmer() //TODO: Uncomment for test
    }

    private fun initComponent() {
        rvSample = findViewById(R.id.rv_sample)
    }

    private fun initListener() {
        btnRefresh.setOnClickListener {
            initDataWithCustomShimmer()
            // initDataWithDefaultShimmer()
        }
    }

    /**
     * Please to implement view id R.id.sample_shimmer mean of shimmer frame layout into param
     * */
    private fun initDataWithCustomShimmer() {
        // Start Shimmer for waiting for the data
        rvSample.startShimmer(10, R.layout.sample_shimmer_effect, R.id.sample_shimmer)
        handler.postDelayed(callback, 5000)
    }

    private fun initDataWithDefaultShimmer() {
        // Start Shimmer for waiting for the data
        rvSample.startShimmer(10)
        handler.postDelayed(callback, 5000)
    }

    private val callback = {
        val list = arrayListOf<SampleData>()
        for (i in 1..100) {
            list.add(SampleData("Key $i", "Value $i"))
        }

        // Re set view Holder
        rvSample.setViewHolderLayout(R.layout.sample_view_holder, list, bindViewHolder)
    }

    private val bindViewHolder = { view: View, data: SampleData ->
        val tvKey = view.findViewById<TextView>(R.id.tv_key)
        val tvValue = view.findViewById<TextView>(R.id.tv_value)
        tvKey.text = data.key
        tvValue.text = data.value
    }
}
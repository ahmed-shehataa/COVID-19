package com.ashehata.covid_19.showSummary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.ashehata.covid_19.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class SummaryActivity : AppCompatActivity() {

    private val viewModel: SummaryViewModel by inject()
    private var mAdapter: SummaryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateUi()
    }

    private fun updateUi() {
        viewModel.viewState.observe(this, Observer {

            pb_loading.visibility = if (it.loading) View.VISIBLE else View.GONE

            pb_refresh.isRefreshing = it.refresh

            if (it.error != null) Toast.makeText(this, "Check internet connection", Toast.LENGTH_SHORT).show()

            //if (it.empty) Toast.makeText(this, "Empty data", Toast.LENGTH_SHORT).show()

            if (it.countries != null) {
                Toast.makeText(this, it.countries?.size.toString(), Toast.LENGTH_SHORT).show()
                // Display the data
                mAdapter = SummaryAdapter(it.countries!!)
                rv_summary.adapter = mAdapter
                mAdapter?.notifyDataSetChanged()

            }

        })
    }
}

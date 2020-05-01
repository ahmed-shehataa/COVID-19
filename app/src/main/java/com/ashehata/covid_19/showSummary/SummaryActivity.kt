package com.ashehata.covid_19.showSummary

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.ashehata.covid_19.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SummaryActivity : AppCompatActivity() {

    private val viewModel: SummaryViewModel by viewModel()
    private var mAdapter: SummaryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateUi()
        setRefresh()
    }

    private fun setRefresh() {
        // Edit res color
        pb_refresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN)

        pb_refresh.setOnRefreshListener {
            viewModel.setRefreshing()
        }
    }

    private fun updateUi() {
        viewModel.viewState.observe(this, Observer {

            pb_loading.visibility = if (it.loading) View.VISIBLE else View.GONE

            pb_refresh.isRefreshing = it.refresh

            //tv_lastUpdate.text = it.lastUpdate
            if (it.lastUpdate.isNotEmpty()) {
                Snackbar.make(parent_view, "Last update: ${it.lastUpdate}",
                    BaseTransientBottomBar.LENGTH_LONG).show()

            }

            if (it.error != null) Toast.makeText(this, getString(R.string.message_error), Toast.LENGTH_SHORT).show()

            //if (it.empty) Toast.makeText(this, "Empty data", Toast.LENGTH_SHORT).show()

            if (it.countries != null) {
                // Display the data
                mAdapter = SummaryAdapter(it.countries!!)
                rv_summary.adapter = mAdapter
                mAdapter?.notifyDataSetChanged()

            }
        })
    }
}

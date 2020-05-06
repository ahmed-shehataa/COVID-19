package com.ashehata.covid_19.showSummary

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.ashehata.covid_19.R
import com.ashehata.covid_19.externals.ErrorType
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.collaps_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SummaryActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val viewModel: SummaryViewModel by viewModel()
    private var mAdapter: SummaryAdapter? = null
    private var upArrow: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editUi()
        updateUi()
        setRefresh()
    }

    private fun editUi() {
        title = ""
        setSupportActionBar(toolbar)
        rv_summary.setHasFixedSize(true)
        val itemDecorator =

            with(DividerItemDecoration(this, DividerItemDecoration.VERTICAL)) {
                setDrawable(ContextCompat.getDrawable(this@SummaryActivity,  R.drawable.divider)!!)
                rv_summary.addItemDecoration(this)
            }
    }

    private fun setRefresh() {
        // Edit res color
        //pb_refresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN)

        pb_refresh.setOnRefreshListener {
            viewModel.setRefreshing()
        }
    }

    private fun updateUi() {
        viewModel.viewState.observe(this, Observer {

            pb_loading.visibility = if (it.loading) View.VISIBLE else View.GONE

            pb_refresh.isRefreshing = it.refresh

            if (it.global != null) {
                // Update first progress
                pb_circular_total_recovered.setProgress(it.global!!.totalRecovered.toDouble(),
                                                        it.global!!.totalConfirmed.toDouble())
                tv_percent_recovered.setText("${it.global!!.getRecoveredPercent()} %" )

                // Update second progress
                pb_circular_total_deaths.setProgress(it.global!!.totalDeaths.toDouble(),
                                                     it.global!!.totalConfirmed.toDouble())
                tv_percent_deaths.setText("${it.global!!.getDeathsPercent()} %" )

            }

            upArrow?.setVisible(it.showUpArrow)

            if (it.lastUpdate.isNotEmpty()) {
                Snackbar.make(parent_view, "Last update: ${it.lastUpdate}",
                    Snackbar.LENGTH_LONG).show()
            }

            val message = when (it.errorType) {
                ErrorType.EmptyField -> getString(R.string.message_empty_field)
                ErrorType.TryAgain -> getString(R.string.message_try_again)
                ErrorType.NoConnection -> getString(R.string.message_error)
                ErrorType.NoCountry -> getString(R.string.message_no_country)
                ErrorType.NoError -> ""
            }

            if (message.isNotEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }

            if (it.countries != null) {
                // Display the data
               if (mAdapter == null) mAdapter = SummaryAdapter(it.countries!!)
                rv_summary.adapter = mAdapter
            }

            if (it.searchCountryPosition != null) {
                rv_summary.smoothScrollToPosition(it.searchCountryPosition!!)
            }

        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.setSearch(query?.trim()?.toLowerCase())
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the search view
        menuInflater.inflate(R.menu.search_view, menu)
        // Get inflated item
        upArrow = menu?.findItem(R.id.ic_up_arrow)
        val searchItem = menu?.findItem(R.id.ic_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ic_up_arrow -> viewModel.goUp{
                // Call this fun if there is data in the recycler view
                rv_summary.smoothScrollToPosition(0)
            }
        }
        return true
    }
}
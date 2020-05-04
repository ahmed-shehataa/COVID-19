package com.ashehata.covid_19.showSummary

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView;
import android.widget.Toast
import androidx.lifecycle.Observer
import com.ashehata.covid_19.R
import com.ashehata.covid_19.externals.ErrorType
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SummaryActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

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

            if (it.lastUpdate.isNotEmpty()) {
                Snackbar.make(parent_view, "Last update: ${it.lastUpdate}",
                    BaseTransientBottomBar.LENGTH_LONG).show()
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
        val searchItem = menu?.findItem(R.id.sv_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

}
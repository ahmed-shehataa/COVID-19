package com.ashehata.covid_19.showSummary

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ashehata.covid_19.R
import com.ashehata.covid_19.models.Countries
import kotlinx.android.synthetic.main.summary_item.view.*

class SummaryAdapter(private val countriesList: List<Countries>) :
    RecyclerView.Adapter<SummaryAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int = countriesList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Bind data
        holder.bind(countriesList.get(position))
    }

    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.summary_item, parent, false)) {

        private var tvCountryCode: TextView? = null
        private var tvCountryName: TextView? = null
        private var tvTotalConfirmed: TextView? = null
        private var tvTotalDeaths: TextView? = null
        private var tvTotalRecovered: TextView? = null
        private var tvNewConfirmed: TextView? = null
        private var tvNewDeaths: TextView? = null
        private var tvNewRecovered: TextView? = null

        init {
            tvCountryCode = itemView.tv_country_code
            tvCountryName = itemView.tv_country_name
            tvTotalConfirmed = itemView.tv_total_confirmed
            tvTotalDeaths = itemView.tv_total_deaths
            tvTotalRecovered = itemView.tv_total_recovered
            tvNewConfirmed = itemView.tv_new_confirmed
            tvNewDeaths = itemView.tv_new_deaths
            tvNewRecovered = itemView.tv_new_recovered
        }

        fun bind(country: Countries) {
            // To bind the views
            tvCountryCode?.text = country.countryCode
            tvCountryName?.text = country.country
            tvTotalConfirmed?.text = country.totalConfirmed.toString()
            tvTotalDeaths?.text = country.totalDeaths.toString()
            tvTotalRecovered?.text = country.totalRecovered.toString()
            tvNewConfirmed?.text = "+ ${country.newConfirmed}"
            tvNewDeaths?.text = "+ ${country.newDeaths}"
            tvNewRecovered?.text = "+ ${country.newRecovered}"
        }

    }
}
package com.ashehata.covid_19.showSummary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ashehata.covid_19.R
import com.ashehata.covid_19.models.Countries

class SummaryAdapter(private val countriesList: List<Countries>) :
    RecyclerView.Adapter<SummaryAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int = countriesList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Bind data
    }

    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.summary_item, parent, false)) {
        /*
         private var mTextView: TextView? = null
        private var mParent: CardView? = null

        init {
            mParent = itemView.cv_parent
            mTextView = itemView.tv_name
        }
         */
        init {

        }

    }
}
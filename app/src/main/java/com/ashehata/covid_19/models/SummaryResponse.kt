package com.ashehata.covid_19.models

import com.google.gson.annotations.SerializedName

data class SummaryResponse (

	@SerializedName("Global") val global : Global,
	@SerializedName("Countries") val countries : List<Countries>,
	@SerializedName("Date") val date : String
)
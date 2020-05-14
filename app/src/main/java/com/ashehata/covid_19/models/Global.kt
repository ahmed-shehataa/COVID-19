package com.ashehata.covid_19.models

import com.google.gson.annotations.SerializedName

data class Global (
	@SerializedName("NewConfirmed") val newConfirmed : Int,
	@SerializedName("TotalConfirmed") val totalConfirmed : Int,
	@SerializedName("NewDeaths") val newDeaths : Int,
	@SerializedName("TotalDeaths") val totalDeaths : Int,
	@SerializedName("NewRecovered") val newRecovered : Int,
	@SerializedName("TotalRecovered") val totalRecovered : Int
) {
	fun getRecoveredPercent(): String =
		( 100 * (totalRecovered.toFloat() / totalConfirmed) ).toInt().toString()


	fun getDeathsPercent(): String =
		( 100 * (totalDeaths.toFloat() / totalConfirmed) ).toInt().toString()
}
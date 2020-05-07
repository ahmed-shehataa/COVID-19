package com.ashehata.covid_19.externals

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import kotlinx.coroutines.withContext


class ConnectionStateMonitor(private val context: Context) : NetworkCallback() {

    private var connectivityManager: ConnectivityManager? = null
    private lateinit var action: ()-> Unit

    private val networkRequest = lazy {
        NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
    }.value


    init {
        connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        register()
    }


    fun register() {
        connectivityManager?.registerNetworkCallback(networkRequest, this)
    }


    fun onConnected(yourAction: () -> Unit) {
        action = yourAction
    }

    // Likewise, you can have a disable method that simply calls ConnectivityManager.unregisterNetworkCallback(NetworkCallback) too.
    override fun onAvailable(network: Network) {
        // Do what you need to do here
        action()
        Log.v("onAvailable" , network.toString())
    }

    fun unregister() {
        connectivityManager?.unregisterNetworkCallback(this)
    }

}
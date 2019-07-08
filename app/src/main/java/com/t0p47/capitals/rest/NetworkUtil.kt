package com.t0p47.capitals.rest

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkUtil{

    companion object{
        fun hasNetwork(context: Context): Boolean{
            val connectivityManager: ConnectivityManager? = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo: NetworkInfo? = connectivityManager?.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

}

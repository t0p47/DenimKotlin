package com.t0p47.capitals.rest.NetworkIterceptor

import android.util.Log
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor: Interceptor {

    var isNetworkActive: Boolean = false

    constructor(isNetworkActive: Observable<Boolean>){
        isNetworkActive.subscribe(
            { _isNetworkActive -> this.isNetworkActive = _isNetworkActive},
            { _error -> Log.e("LOG_TAG", "Error: ${_error.message}")}
        )
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isNetworkActive){
            throw NoConnectivityException()
        }else{
            val response = chain.proceed(chain.request())
            return response
        }
    }

}
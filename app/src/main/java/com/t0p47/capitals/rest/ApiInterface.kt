package com.t0p47.capitals.rest

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.content.ContextCompat.getSystemService
import com.t0p47.capitals.model.CapitalResponse
import com.t0p47.capitals.rest.NetworkIterceptor.ConnectivityInterceptor
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface ApiInterface {

    @GET("1717200/raw")
    fun getCapitals(): Observable<CapitalResponse>

    companion object Factory{

        val BASE_URL = "https://gitlab.com/snippets/"
        fun create(context: Context): ApiInterface{
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(provideOkHttpClient(context))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiInterface::class.java)
        }

        private fun provideOkHttpClient(context: Context): OkHttpClient{
            var okHttpClientBuilder = OkHttpClient.Builder()
            okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
            okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
            okHttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS)
            okHttpClientBuilder.addInterceptor(ConnectivityInterceptor(Observable.just(NetworkUtil.hasNetwork(context))))

            /*okHttpClientBuilder.addInterceptor(object: NetworkConnectionInterceptor() {
                override fun isInternetAvailable(): Boolean{
                    return isInternetAvailable()
                }

                override fun onInternetUnavailable(){
                    if(mInternetConnectionListener != null){
                        mInternetConnectionListener.onInternetUnavailable()
                    }
                }
            }) */

            return okHttpClientBuilder.build()
        }

    }

}
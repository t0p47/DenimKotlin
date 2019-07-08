package com.t0p47.capitals.model

import android.content.Context
import com.t0p47.capitals.rest.ApiInterface
import io.reactivex.Observable

class CapitalRepository(val context: Context){

	fun getRepositories(): Observable<CapitalResponse> {
		return ApiInterface.create(context).getCapitals()
	}

}
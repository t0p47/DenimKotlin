package com.t0p47.capitals.model

import com.t0p47.capitals.rest.ApiInterface
import io.reactivex.Observable

class CapitalRepository(val apiInterface: ApiInterface){

	fun getRepositories(): Observable<CapitalResponse> {
		return ApiInterface.create().getCapitals()
	}

}
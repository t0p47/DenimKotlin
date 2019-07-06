package com.t0p47.capitals.model

import com.google.gson.annotations.SerializedName

class CapitalResponse {

    @SerializedName("response")
    var capitals: ArrayList<Capital>? = null
}
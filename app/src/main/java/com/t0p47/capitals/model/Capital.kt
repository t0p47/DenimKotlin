package com.t0p47.capitals.model

import com.google.gson.annotations.SerializedName

class Capital {

    @SerializedName("country")
    var country: String? = null

    @SerializedName("capital")
    var capital: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("images")
    var images: ArrayList<String>? = null


}
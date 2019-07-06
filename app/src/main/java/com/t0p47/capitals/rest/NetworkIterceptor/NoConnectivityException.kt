package com.t0p47.capitals.rest.NetworkIterceptor

import java.io.IOException

class NoConnectivityException: IOException() {

    override fun getLocalizedMessage(): String {
        return "No network available, please check WiFi or Data connection"
    }

}
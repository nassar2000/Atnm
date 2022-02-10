package com.airtouch.atnm

import android.app.Application
import com.airtouch.atnm.api.ApiClient
import com.jakewharton.threetenabp.AndroidThreeTen

class AtnmApp : Application() {

    override fun onCreate() {
        super.onCreate()

        ApiClient.initClient(context = applicationContext)
        AndroidThreeTen.init(this)
    }
}
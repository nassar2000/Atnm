package com.airtouch.atnm.api

import com.airtouch.atnm.models.ExchangeInfo
import retrofit2.Call
import retrofit2.http.GET

interface ExchangeApi {

    @GET("1")
    fun getExchangeInfo(): Call<ExchangeInfo>
}
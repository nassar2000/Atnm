package com.airtouch.atnm.api

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


object ApiClient {

    private const val BASE_URL = "https://0gzg3.mocklab.io/json/"

    lateinit var exchangeApiClient: ExchangeApi

    fun initClient(context: Context) {
        val client = OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor.Builder(context).build())
            .build()

        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(client)
            .build()

        exchangeApiClient = retrofit.create(ExchangeApi::class.java)
    }
}

inline fun <T> Call<T>.executeRequest(
    crossinline onSuccess: (T?) -> Unit,
    crossinline onError: (Throwable) -> Unit
) {
    enqueue(object : Callback<T> {
        override fun onResponse(
            call: Call<T>,
            response: Response<T>
        ) {
            onSuccess(response.body())
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            onError(t)
        }
    })
}
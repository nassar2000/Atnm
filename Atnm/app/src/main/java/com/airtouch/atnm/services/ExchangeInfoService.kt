package com.airtouch.atnm.services

import com.airtouch.atnm.api.ApiClient
import com.airtouch.atnm.api.executeRequest
import com.airtouch.atnm.models.ExchangeInfo

class ExchangeInfoService {

    private val exchangedApiClient = ApiClient.exchangeApiClient

    fun loadExchangeInfo(onSuccess: (ExchangeInfo?) -> Unit, onError: (Throwable) -> Unit) {
        exchangedApiClient.getExchangeInfo()
            .executeRequest(
                onSuccess = onSuccess,
                onError = onError
            )
    }
}
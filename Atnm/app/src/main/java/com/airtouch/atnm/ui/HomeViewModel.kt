package com.airtouch.atnm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.airtouch.atnm.models.ExchangeInfo
import com.airtouch.atnm.services.ExchangeInfoService

class HomeViewModel : ViewModel() {

    private val _exchangeInfoLiveData = MutableLiveData<ExchangeInfo>()
    val exchangeInfoLiveData: LiveData<ExchangeInfo> = _exchangeInfoLiveData

    private val _exchangeInfoErrorLiveData = MutableLiveData<Throwable>()
    val exchangeInfoErrorLiveData: LiveData<Throwable> = _exchangeInfoErrorLiveData

    private val exchangeInfoService = ExchangeInfoService()

    fun loadExchangeInfo() {
        exchangeInfoService.loadExchangeInfo(onSuccess = {
            _exchangeInfoLiveData.postValue(it)
        },
            onError = {
                _exchangeInfoErrorLiveData.postValue(it)
            })
    }
}


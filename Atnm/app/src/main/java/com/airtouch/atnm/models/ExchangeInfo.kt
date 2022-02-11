package com.airtouch.atnm.models

import kotlinx.serialization.Serializable

@Serializable
data class ExchangeInfo(
    val rates: List<Rates>,
    val pairs: List<Pairs>
)

@Serializable
data class Rates(
    val from: String,
    val to: String,
    val rate: Float
)

@Serializable
data class Pairs(
    val from: String,
    val to: String
)

data class PairsWitheRate(
    val from: String,
    val to: String,
    val rate: String
)
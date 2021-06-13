package ru.iskhakoff.data.remote.models.response

data class InvestItem(
    val id: Int,
    val created: String,
    val name: String,
    val description: String,
    val deleted: Boolean,
    val sum: Double,
    val stocks: List<Stock>,
    var owned: Double = 0.0
)

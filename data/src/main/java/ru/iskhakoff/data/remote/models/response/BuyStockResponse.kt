package ru.iskhakoff.data.remote.models.response

data class BuyStockResponse(
    val type: String,
    val sum: Double,
    val user: UserBuyStockResponse,
    val project: ProjectBuyStockResponse,
    val created: String,
    val name: String,
    val id: Int
)

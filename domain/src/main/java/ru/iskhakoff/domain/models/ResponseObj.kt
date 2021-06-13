package ru.iskhakoff.domain.models

data class ResponseObj<T>(
    val code: Int,
    val message: String,
    val body: T?
)

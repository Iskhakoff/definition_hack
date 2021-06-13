package ru.iskhakoff.data.remote.models.request

data class UserSignUp(
    val name: String,
    val email: String,
    val password: String
)

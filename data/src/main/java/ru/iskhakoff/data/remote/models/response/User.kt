package ru.iskhakoff.data.remote.models.response

data class User(
    val id: Int,
    val created: String,
    val name: String,
    val deleted: Boolean,
    val email: String,
    val token: String
)

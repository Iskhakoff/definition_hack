package ru.iskhakoff.domain

interface PrefsRepository {
    fun getToken() : String
    fun setToken(token : String)
    fun removeToken()
}
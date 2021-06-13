package ru.iskhakoff.data.storage.preferences

interface PrefsProvider {
    fun setToken(token: String)
    fun getToken() : String
    fun removePrefs()
    fun setUserId(id: Int)
    fun getUserId(): Int
}
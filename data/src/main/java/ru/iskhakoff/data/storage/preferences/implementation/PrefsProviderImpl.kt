package ru.iskhakoff.data.storage.preferences.implementation

import android.content.Context
import ru.iskhakoff.data.storage.preferences.PrefsProvider
import ru.iskhakoff.data.storage.preferences.delegate.int
import ru.iskhakoff.data.storage.preferences.delegate.string

class PrefsProviderImpl(context: Context) : PrefsProvider{

    companion object{
        const val PREF_NAME = "definition"
    }

    private val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private var userToken by preferences.string()
    private var idUser by preferences.int()

    override fun setToken(token: String) {
        userToken = token
    }

    override fun getToken() = userToken

    override fun removePrefs() {
        preferences.edit().clear().apply()
    }

    override fun setUserId(id: Int) {
        idUser = id
    }

    override fun getUserId(): Int = idUser
}
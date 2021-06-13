package ru.iskhakoff.domain.implementation

import ru.iskhakoff.data.storage.preferences.PrefsProvider
import ru.iskhakoff.domain.PrefsRepository

class PrefsRepositoryImpl(private val prefsProvider: PrefsProvider) : PrefsRepository {
    override fun getToken(): String {
        return prefsProvider.getToken()
    }

    override fun setToken(token: String) {
        return prefsProvider.setToken(token)
    }

    override fun removeToken() {
        prefsProvider.removePrefs()
    }
}
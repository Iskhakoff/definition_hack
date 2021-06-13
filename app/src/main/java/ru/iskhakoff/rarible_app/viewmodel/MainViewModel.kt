package ru.iskhakoff.rarible_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.iskhakoff.domain.ApiRepository
import ru.iskhakoff.domain.PrefsRepository
import ru.iskhakoff.rarible_app.utils.SingleLiveEvent

class MainViewModel(private val apiRepository: ApiRepository, private val prefsRepository: PrefsRepository): ViewModel() {

    private companion object {
        const val TAG = "MainViewModel"
    }

    private val _isCorrectToken = SingleLiveEvent<Boolean>()

    val isCorrectToken : LiveData<Boolean>
        get() = _isCorrectToken

    fun checkToken() {
        viewModelScope.launch(handler) {
            val response = apiRepository.checkToken(prefsRepository.getToken())
            _isCorrectToken.postValue(response.body!!)
        }
    }

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "${throwable.cause}", throwable)
        _isCorrectToken.value = false
    }
}
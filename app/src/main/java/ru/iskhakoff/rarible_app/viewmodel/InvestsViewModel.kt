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
import ru.iskhakoff.rarible_app.utils.ViewState

class InvestsViewModel(private val apiRepository: ApiRepository, private val prefsRepository: PrefsRepository): ViewModel() {

    companion object {
        const val TAG = "InvestsViewModel"
    }

    private val _state = SingleLiveEvent<ViewState>()

    val state : LiveData<ViewState>
        get() = _state

    fun getInvests() {
        _state.value = ViewState.Loading
        viewModelScope.launch(handler) {
            val response = apiRepository.getInvests(prefsRepository.getToken())
            when (response.code) {
                200 -> {
                    _state.postValue(ViewState.Success(response))
                }
                401 -> {
                    _state.postValue(ViewState.UnauthorizedError)
                }
                404 -> {
                    _state.postValue(ViewState.ErrorInvalidData)
                }
                else -> {
                    _state.postValue(ViewState.RemoteError)
                }
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "${throwable.cause}", throwable)
        _state.value = ViewState.ConnectError
    }
}
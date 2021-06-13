package ru.iskhakoff.rarible_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.iskhakoff.data.remote.ApiProvider
import ru.iskhakoff.domain.ApiRepository
import ru.iskhakoff.domain.PrefsRepository
import ru.iskhakoff.rarible_app.utils.SingleLiveEvent
import ru.iskhakoff.rarible_app.utils.ViewState

class CreateProjectViewModel(private val apiRepository: ApiRepository, private val prefsRepository: PrefsRepository) : ViewModel() {

    private companion object {
        const val TAG = "CreateProjectViewModel"
    }

    private val _state = SingleLiveEvent<ViewState>()

    val state : LiveData<ViewState>
        get() = _state

    fun createProject(name: String, description: String, sum: Double){
        _state.value = ViewState.Loading
        viewModelScope.launch(handler) {
            val response = apiRepository.createProject(prefsRepository.getToken(), name, description, sum)
            when (response.code) {
                201 -> {
                    _state.postValue(ViewState.Success(response))
                }
                else -> {
                    _state.postValue(ViewState.ConnectError)
                }
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "${throwable.cause}", throwable)
        _state.value = ViewState.ConnectError
    }
}
package ru.iskhakoff.rarible_app.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.iskhakoff.domain.ApiRepository
import ru.iskhakoff.domain.PrefsRepository
import ru.iskhakoff.rarible_app.utils.SingleLiveEvent
import ru.iskhakoff.rarible_app.utils.ViewState

class ProjectViewModel(private val apiRepository: ApiRepository, private val prefsRepository: PrefsRepository): ViewModel() {

    private companion object {
        const val TAG = "ProjectViewModel"
    }

    private val _state = SingleLiveEvent<ViewState>()
    private val _buyState = SingleLiveEvent<ViewState>()

    val state : LiveData<ViewState>
        get() = _state

    val buyState : LiveData<ViewState>
        get() = _buyState

    fun getProjectById(id: Int){
        _state.value = ViewState.Loading
        viewModelScope.launch(handler) {
            val response = apiRepository.getProjectById(prefsRepository.getToken(), id)
            when (response.code){
                200 -> {
                    _state.postValue(ViewState.Success(response))
                }
                401 -> {
                    _state.postValue(ViewState.UnauthorizedError)
                }
                else -> {
                    _state.postValue(ViewState.ConnectError)
                }
            }
        }
    }

    fun buyAsset(projectId: Int, sum: Double){
        _buyState.value = ViewState.Loading
        viewModelScope.launch(handler) {
            val response = apiRepository.buyAsset(prefsRepository.getToken(), sum, projectId)
            when (response.code) {
                201 -> _buyState.postValue(ViewState.Success(response))
                else -> _buyState.postValue(ViewState.ConnectError)
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "${throwable.cause}", throwable)
        _state.value = ViewState.ConnectError
    }
}
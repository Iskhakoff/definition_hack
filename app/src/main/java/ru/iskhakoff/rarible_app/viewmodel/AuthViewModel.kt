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

class AuthViewModel(private val apiRepository: ApiRepository, private val prefsRepository: PrefsRepository) : ViewModel() {

    private companion object {
        private const val TAG = "AuthViewModel"
    }

    private val _stateSignIn = SingleLiveEvent<ViewState>()
    private val _stateSignUp = SingleLiveEvent<ViewState>()

    val stateSignIn: LiveData<ViewState>
        get() = _stateSignIn

    val stateSignUp: LiveData<ViewState>
        get() = _stateSignUp

    fun signIn(name: String, password: String) {
        if (name.isNotBlank() && password.isNotBlank()) {
            _stateSignIn.value = ViewState.Loading
            viewModelScope.launch(handler) {
                val response = apiRepository.signIn(name, password)
                when (response.code) {
                    200 -> {
                        _stateSignIn.postValue(ViewState.Success(response))
                        prefsRepository.setToken(response.body!!.token)
                    }
                    404 -> {
                        _stateSignIn.postValue(ViewState.ErrorInvalidData)
                    }
                    in 500..502 -> {
                        _stateSignIn.postValue(ViewState.RemoteError)
                    }
                    else -> {
                        _stateSignIn.postValue(ViewState.ConnectError)
                    }
                }
            }
        } else {
            _stateSignIn.value = ViewState.ErrorNotFilledFields
        }
    }

    fun signUp(name: String, email: String, password: String, confirmedPassword: String) {
        if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmedPassword.isNotBlank()) {
            if (password == confirmedPassword) {
                _stateSignUp.value = ViewState.Loading
                viewModelScope.launch(handler) {
                    val responseCheckLogin = apiRepository.checkLogin(name)
                    if (!responseCheckLogin.body!!) {
                        val response = apiRepository.signUp(name, email, password)
                        when (response.code) {
                            200 -> {
                                _stateSignUp.postValue(ViewState.Success(response))
                                prefsRepository.setToken(response.body!!.token)
                            }
                            404 -> {
                                _stateSignUp.postValue(ViewState.ErrorInvalidData)
                            }
                            in 500..502 -> {
                                _stateSignUp.postValue(ViewState.RemoteError)
                            }
                            else -> {
                                _stateSignUp.postValue(ViewState.ConnectError)
                            }
                        }
                    } else {
                        _stateSignUp.postValue(ViewState.ErrorLoginAlreadyExists)
                    }
                }
            } else {
                _stateSignUp.value = ViewState.ErrorPasswordMismatch
            }
        } else {
            _stateSignUp.value = ViewState.ErrorNotFilledFields
        }
    }

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "${throwable.cause}", throwable)
        _stateSignIn.postValue(ViewState.ConnectError)
    }
}
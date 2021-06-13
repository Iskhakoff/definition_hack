package ru.iskhakoff.rarible_app.utils

import ru.iskhakoff.domain.models.ResponseObj

sealed class ViewState {
    object Loading: ViewState()
    object ConnectError: ViewState()
    object RemoteError: ViewState()
    object ErrorNotFilledFields: ViewState()
    object ErrorInvalidData: ViewState()
    object ErrorPasswordMismatch: ViewState()
    object ErrorLoginAlreadyExists: ViewState()
    object UnauthorizedError: ViewState()
    class Success(val obj: ResponseObj<*>): ViewState()
}

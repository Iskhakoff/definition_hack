package ru.iskhakoff.rarible_app.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.iskhakoff.domain.ApiRepository
import ru.iskhakoff.domain.PrefsRepository
import ru.iskhakoff.rarible_app.viewmodel.CreateProjectViewModel

@Suppress("UNCHECKED_CAST")
class CreateProjectViewModelFactory(private val apiRepository: ApiRepository, private val prefsRepository: PrefsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreateProjectViewModel(apiRepository, prefsRepository) as T
    }
}
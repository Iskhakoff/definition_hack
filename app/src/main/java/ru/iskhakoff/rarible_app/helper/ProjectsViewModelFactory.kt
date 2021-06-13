package ru.iskhakoff.rarible_app.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.iskhakoff.domain.ApiRepository
import ru.iskhakoff.domain.PrefsRepository
import ru.iskhakoff.rarible_app.viewmodel.ProjectsViewModel

@Suppress("UNCHECKED_CAST")
class ProjectsViewModelFactory(private val apiRepository: ApiRepository, private val prefsRepository: PrefsRepository):
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProjectsViewModel(apiRepository, prefsRepository) as T
    }

}
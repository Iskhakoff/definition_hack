package ru.iskhakoff.rarible_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import ru.iskhakoff.data.remote.ApiProvider
import ru.iskhakoff.data.remote.implementation.ApiProviderImpl
import ru.iskhakoff.data.storage.preferences.PrefsProvider
import ru.iskhakoff.data.storage.preferences.implementation.PrefsProviderImpl
import ru.iskhakoff.domain.ApiRepository
import ru.iskhakoff.domain.PrefsRepository
import ru.iskhakoff.domain.implementation.ApiRepositoryImpl
import ru.iskhakoff.domain.implementation.PrefsRepositoryImpl
import ru.iskhakoff.domain.mapper.MapFromApiToDomain
import ru.iskhakoff.domain.mapper.implementation.MapFromApiToDomainImpl
import ru.iskhakoff.rarible_app.R

class ProfileFragment : Fragment() {

    private lateinit var logout: Button

    private lateinit var apiProvider : ApiProvider
    private lateinit var mapper : MapFromApiToDomain
    private lateinit var apiRepository : ApiRepository
    private lateinit var prefsProvider : PrefsProvider
    private lateinit var prefsRepository : PrefsRepository

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiProvider = ApiProviderImpl()
        mapper = MapFromApiToDomainImpl()
        apiRepository = ApiRepositoryImpl(apiProvider, mapper)
        prefsProvider = PrefsProviderImpl(requireContext())
        prefsRepository = PrefsRepositoryImpl(prefsProvider)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)
        activity?.let {
            navController = it.findNavController(R.id.fragment_container)
        }
        logout = rootView.findViewById(R.id.logout_btn)

        logout.setOnClickListener {
            prefsProvider.removePrefs()
            navController.navigate(MainFragmentDirections.actionLogout())
        }
        return rootView
    }
}
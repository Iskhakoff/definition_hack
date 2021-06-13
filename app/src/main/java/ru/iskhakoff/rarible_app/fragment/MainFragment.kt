package ru.iskhakoff.rarible_app.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
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
import ru.iskhakoff.rarible_app.helper.MainViewModelFactory
import ru.iskhakoff.rarible_app.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private lateinit var navController : NavController

    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var titleToolbar : TextView
    private lateinit var mViewModel: MainViewModel
    private lateinit var mCreateNewProject: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiProvider : ApiProvider = ApiProviderImpl()
        val mapper : MapFromApiToDomain = MapFromApiToDomainImpl()
        val apiRepository : ApiRepository = ApiRepositoryImpl(apiProvider, mapper)
        val prefsProvider : PrefsProvider = PrefsProviderImpl(requireContext())
        val prefsRepository : PrefsRepository = PrefsRepositoryImpl(prefsProvider)
        mViewModel = ViewModelProvider(this, MainViewModelFactory(apiRepository, prefsRepository)).get(MainViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
        setupUI(rootView)
        activity?.let {
            navController = it.findNavController(R.id.fragment_container)
        }

        val navHostFragment = childFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        val mainNavController = navHostFragment.navController

        mainNavController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_projects -> {
                    titleToolbar.text = "Projects"
                    mCreateNewProject.visibility = View.VISIBLE
                }
                R.id.navigation_invests -> {
                    titleToolbar.text = "Investments"
                    mCreateNewProject.visibility = View.GONE
                }
                R.id.navigation_profile -> {
                    titleToolbar.text = "Profile"
                    mCreateNewProject.visibility = View.GONE
                }
            }
        }

        NavigationUI.setupWithNavController(bottomNavigation, mainNavController)

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            if (item.itemId != bottomNavigation.selectedItemId) {
                NavigationUI.onNavDestinationSelected(item, mainNavController)
            }
            true
        }

        mViewModel.isCorrectToken.observe(viewLifecycleOwner, {
            if (!it) {
                navController.navigate(MainFragmentDirections.actionLogout())
            }
        })

        mViewModel.checkToken()

        mCreateNewProject.setOnClickListener {
            navController.navigate(R.id.createProject)
        }

        return rootView
    }

    private fun setupUI(rootView: View) {
        bottomNavigation = rootView.findViewById(R.id.bottom_navigation)
        titleToolbar = rootView.findViewById(R.id.title_toolbar_text)
        mCreateNewProject = rootView.findViewById(R.id.create_new_project_btn)
    }
}
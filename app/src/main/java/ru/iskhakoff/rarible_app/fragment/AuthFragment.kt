package ru.iskhakoff.rarible_app.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
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
import ru.iskhakoff.rarible_app.adapter.AuthViewPagerAdapter
import ru.iskhakoff.rarible_app.utils.ZoomTransformer

class AuthFragment : Fragment(), SignInFragment.IClickLoginBtn, SignUpFragment.IClickRegisterBtn, SuccessRegisterBottomSheetFragment.ISuccessRegister {

    private lateinit var apiProvider: ApiProvider
    private lateinit var mapper: MapFromApiToDomain
    private lateinit var apiRepository: ApiRepository
    private lateinit var prefsProvider: PrefsProvider
    private lateinit var prefsRepository: PrefsRepository

    private lateinit var mTitleText : TextView
    private lateinit var mTabLayout : TabLayout
    private lateinit var mViewPager : ViewPager2
    private lateinit var mAppBar : AppBarLayout

    private lateinit var navController: NavController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        apiProvider = ApiProviderImpl()
        mapper = MapFromApiToDomainImpl()
        apiRepository = ApiRepositoryImpl(apiProvider, mapper)
        prefsProvider = PrefsProviderImpl(context)
        prefsRepository = PrefsRepositoryImpl(prefsProvider)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_auth, container, false)
        activity?.let {
            navController = it.findNavController(R.id.fragment_container)
        }
        setupUI(rootView)
        setupViewPager()
        return rootView
    }


    private fun setupViewPager() {
        val adapter = AuthViewPagerAdapter(this)
        adapter.addFragment(SignInFragment.newInstance(this, apiRepository, prefsRepository))
        adapter.addFragment(SignUpFragment.newInstance(this, apiRepository, prefsRepository))
        mViewPager.adapter = adapter
        mViewPager.setPageTransformer(ZoomTransformer())
        TabLayoutMediator(mTabLayout, mViewPager) {tab, position ->
            when (position) {
                0 -> tab.text = "Sign In"
                1 -> tab.text = "Sign Up"
            }
        }.attach()
    }

    private fun setupUI(rootView: View) {
        mTitleText  = rootView.findViewById(R.id.title_app_bar)
        mTabLayout  = rootView.findViewById(R.id.tabs_app_bar)
        mViewPager  = rootView.findViewById(R.id.view_pager_auth)
        mAppBar     = rootView.findViewById(R.id.app_bar)
    }

    override fun login() {
        navController.navigate(AuthFragmentDirections.actionLogin())
    }

    override fun register() {
        val successRegisterFragment = SuccessRegisterBottomSheetFragment.newInstance(this)
        successRegisterFragment.isCancelable = false
        successRegisterFragment.showNow(parentFragmentManager, "SUCCESS_REGISTER_FRAGMENT")
    }

    override fun navigateToMainScreen() {
        navController.navigate(AuthFragmentDirections.actionLogin())
    }


}
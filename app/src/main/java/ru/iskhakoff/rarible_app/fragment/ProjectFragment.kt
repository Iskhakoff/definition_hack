package ru.iskhakoff.rarible_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import ru.iskhakoff.data.remote.ApiProvider
import ru.iskhakoff.data.remote.implementation.ApiProviderImpl
import ru.iskhakoff.data.remote.models.response.ProjectItem
import ru.iskhakoff.data.storage.preferences.PrefsProvider
import ru.iskhakoff.data.storage.preferences.implementation.PrefsProviderImpl
import ru.iskhakoff.domain.ApiRepository
import ru.iskhakoff.domain.PrefsRepository
import ru.iskhakoff.domain.implementation.ApiRepositoryImpl
import ru.iskhakoff.domain.implementation.PrefsRepositoryImpl
import ru.iskhakoff.domain.mapper.MapFromApiToDomain
import ru.iskhakoff.domain.mapper.implementation.MapFromApiToDomainImpl
import ru.iskhakoff.rarible_app.R
import ru.iskhakoff.rarible_app.helper.ProjectViewModelFactory
import ru.iskhakoff.rarible_app.utils.ViewState
import ru.iskhakoff.rarible_app.viewmodel.ProjectViewModel
import ru.iskhakoff.rarible_app.viewmodel.ProjectsViewModel

class ProjectFragment : Fragment() {

    private lateinit var mProjectTitle: TextView
    private lateinit var mProjectDesc: TextView
    private lateinit var mProjectContribution: TextView
    private lateinit var mProjectShares: TextView
    private lateinit var mSellBtn: Button
    private lateinit var mBuyMoreBtn: Button
    private lateinit var mProgress: ProgressBar
    private lateinit var mMainContainer: ViewGroup
    private lateinit var mBackBtn: ImageView

    private lateinit var mViewModel: ProjectViewModel
    private lateinit var navController: NavController
    private var projectId = -1;
    private lateinit var apiProvider : ApiProvider
    private lateinit var mapper : MapFromApiToDomain
    private lateinit var apiRepository : ApiRepository
    private lateinit var prefsProvider : PrefsProvider
    private lateinit var prefsRepository : PrefsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiProvider = ApiProviderImpl()
        mapper = MapFromApiToDomainImpl()
        apiRepository = ApiRepositoryImpl(apiProvider, mapper)
        prefsProvider = PrefsProviderImpl(requireContext())
        prefsRepository = PrefsRepositoryImpl(prefsProvider)
        mViewModel = ViewModelProvider(this, ProjectViewModelFactory(apiRepository, prefsRepository)).get(ProjectViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_project, container, false)
        setupUI(rootView)
        activity?.let {
            navController = it.findNavController(R.id.fragment_container)
        }
        arguments?.let {
            projectId = it.getInt("project_id")
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.getProjectById(projectId)

        mViewModel.state.observe(viewLifecycleOwner, {
            render(it)
        })

        mBackBtn.setOnClickListener {
            navController.navigateUp()
        }

        mBuyMoreBtn.setOnClickListener {
            val bottomSheetFragment = BuyAssetBottomSheetFragment.newInstance(apiRepository, prefsRepository, projectId)
            bottomSheetFragment.showNow(parentFragmentManager, "buy_asset_fragment")
        }
    }

    private fun render(state: ViewState) {
        when (state) {
            is ViewState.Loading -> {
                mProgress.visibility = View.VISIBLE
                mMainContainer.visibility = View.GONE
            }
            is ViewState.Success -> {
                state.obj.body as ProjectItem
                mProgress.visibility = View.GONE
                mMainContainer.visibility = View.VISIBLE
                mProjectTitle.text = (state.obj.body as ProjectItem).name
                mProjectDesc.text = (state.obj.body as ProjectItem).description
                mProjectContribution.text = (state.obj.body as ProjectItem).sum.toString()
                mProjectShares.text = (state.obj.body as ProjectItem).collected.toString()
            }
        }
    }

    private fun setupUI(rootView: View) {
        mProjectTitle = rootView.findViewById(R.id.project_detail_title)
        mProjectDesc = rootView.findViewById(R.id.project_detail_description)
        mProjectContribution = rootView.findViewById(R.id.project_contribution_value)
        mProjectShares = rootView.findViewById(R.id.project_shapes_value)
        mSellBtn = rootView.findViewById(R.id.project_sell_assets)
        mBuyMoreBtn = rootView.findViewById(R.id.project_buy_more_assets)
        mProgress = rootView.findViewById(R.id.project_detail_progress)
        mMainContainer = rootView.findViewById(R.id.project_detail_main_container)
        mBackBtn = rootView.findViewById(R.id.project_fragment_back_btn)
    }
}
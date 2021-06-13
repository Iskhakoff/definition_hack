package ru.iskhakoff.rarible_app.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import ru.iskhakoff.rarible_app.adapter.ProjectsAdapter
import ru.iskhakoff.rarible_app.adapter.decoration.ProjectsItemsDecoration
import ru.iskhakoff.rarible_app.helper.ProjectsViewModelFactory
import ru.iskhakoff.rarible_app.utils.ViewState
import ru.iskhakoff.rarible_app.viewmodel.ProjectsViewModel

class ProjectsFragment : Fragment(), ProjectsAdapter.IClickProject {

    private lateinit var mViewModel: ProjectsViewModel
    private lateinit var navController: NavController

    private lateinit var mRecycler: RecyclerView
    private lateinit var adapter: ProjectsAdapter
    private lateinit var mProgress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiProvider : ApiProvider = ApiProviderImpl()
        val mapper : MapFromApiToDomain = MapFromApiToDomainImpl()
        val apiRepository : ApiRepository = ApiRepositoryImpl(apiProvider, mapper)
        val prefsProvider : PrefsProvider = PrefsProviderImpl(requireContext())
        val prefsRepository : PrefsRepository = PrefsRepositoryImpl(prefsProvider)
        mViewModel = ViewModelProvider(this, ProjectsViewModelFactory(apiRepository, prefsRepository)).get(ProjectsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_projects, container, false)
        activity?.let {
            navController = it.findNavController(R.id.fragment_container)
        }
        setupUI(rootView)
        setupRecycler()
        return rootView
    }

    private fun setupRecycler() {
        adapter = ProjectsAdapter(layoutInflater, this)
        mRecycler.addItemDecoration(ProjectsItemsDecoration(
            ContextCompat.getDrawable(mRecycler.context, R.drawable.divider_list)!!, resources.getDimension(R.dimen.fragment_horizontal_margin).toInt())
        )
        mRecycler.layoutManager = LinearLayoutManager(context)
        mRecycler.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.getProjects()

        mViewModel.state.observe(viewLifecycleOwner, {
            when (it) {
                is ViewState.Loading -> {
                    mProgress.visibility = View.VISIBLE
                    mRecycler.visibility = View.GONE
                }
                is ViewState.Success -> {
                    mProgress.visibility = View.GONE
                    mRecycler.visibility = View.VISIBLE
                    adapter.submitList(it.obj.body as List<ProjectItem>)
                }
            }
        })
    }

    override fun onItemClickProjects(id: Int) {
        navController.navigate(R.id.projectDetail, bundleOf("project_id" to id))
    }

    private fun setupUI(rootView: View) {
        mRecycler = rootView.findViewById(R.id.projects_list)
        mProgress = rootView.findViewById(R.id.projects_fragment_progress_bar)
    }
}
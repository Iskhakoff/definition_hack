package ru.iskhakoff.rarible_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
import ru.iskhakoff.rarible_app.helper.CreateProjectViewModelFactory
import ru.iskhakoff.rarible_app.utils.ViewState
import ru.iskhakoff.rarible_app.viewmodel.CreateProjectViewModel

class CreateProjectFragment : Fragment() {

    private lateinit var mBackBtn: ImageView
    private lateinit var mTitleEt: EditText
    private lateinit var mDescriptionEt: EditText
    private lateinit var mSumEt: EditText
    private lateinit var mProgress: ProgressBar
    private lateinit var mCreateProjectBtn: Button

    private lateinit var mViewModel : CreateProjectViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiProvider : ApiProvider = ApiProviderImpl()
        val mapper : MapFromApiToDomain = MapFromApiToDomainImpl()
        val apiRepository : ApiRepository = ApiRepositoryImpl(apiProvider, mapper)
        val prefsProvider : PrefsProvider = PrefsProviderImpl(requireContext())
        val prefsRepository : PrefsRepository = PrefsRepositoryImpl(prefsProvider)
        mViewModel = ViewModelProvider(this, CreateProjectViewModelFactory(apiRepository, prefsRepository)).get(CreateProjectViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_create_project, container, false)
        setupUI(rootView)
        activity?.let {
            navController = it.findNavController(R.id.fragment_container)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCreateProjectBtn.setOnClickListener {
            mViewModel.createProject(
                mTitleEt.text.toString(),
                mDescriptionEt.text.toString(),
                mSumEt.text.toString().toDouble()
            )
        }

        mBackBtn.setOnClickListener {
            navController.navigateUp()
        }

        mViewModel.state.observe(viewLifecycleOwner, {
            render(it)
        })
    }

    private fun render(state: ViewState) {
        when (state) {
            is ViewState.Loading -> {
                mCreateProjectBtn.visibility = View.INVISIBLE
                mProgress.visibility = View.VISIBLE
            }
            is ViewState.Success -> {
                mProgress.visibility = View.INVISIBLE
                mCreateProjectBtn.visibility = View.VISIBLE
                val successFragment = SuccessCreatingProjectBottomSheetFragment.newInstance()
                successFragment.showNow(parentFragmentManager, "success_creating_project")
            }
        }
    }

    private fun setupUI(rootView: View) {
        mBackBtn = rootView.findViewById(R.id.create_project_fragment_back_btn)
        mTitleEt = rootView.findViewById(R.id.create_project_title_et)
        mDescriptionEt = rootView.findViewById(R.id.create_project_description_et)
        mSumEt = rootView.findViewById(R.id.create_project_sum_et)
        mProgress = rootView.findViewById(R.id.create_project_progress)
        mCreateProjectBtn = rootView.findViewById(R.id.create_project_btn)
    }
}
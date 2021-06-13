package ru.iskhakoff.rarible_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStructure
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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
import ru.iskhakoff.rarible_app.helper.ProjectViewModelFactory
import ru.iskhakoff.rarible_app.utils.ViewState
import ru.iskhakoff.rarible_app.viewmodel.ProjectViewModel

class BuyAssetBottomSheetFragment(private val apiRepository: ApiRepository, private val prefsRepository: PrefsRepository) : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(apiRepository: ApiRepository, prefsRepository: PrefsRepository, projectId: Int): BuyAssetBottomSheetFragment {
            val args = Bundle()
            args.putInt("project_id", projectId)
            val fragment = BuyAssetBottomSheetFragment(apiRepository, prefsRepository)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mMainContainer: ViewGroup
    private lateinit var mSumField: EditText
    private lateinit var mBuyBtn: Button
    private lateinit var mSuccessBuyingContainer: ViewGroup
    private lateinit var mNavigateToMainScreenBtn: Button

    private lateinit var mViewModel: ProjectViewModel
    private lateinit var navController: NavController

    private var projectId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.buy_asset_fragment, container, false)
        setupUI(rootView)
        mViewModel = ViewModelProvider(this, ProjectViewModelFactory(apiRepository, prefsRepository)).get(ProjectViewModel::class.java)
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

        mBuyBtn.setOnClickListener {
            mViewModel.buyAsset(projectId, mSumField.text.toString().toDouble())
        }

        mNavigateToMainScreenBtn.setOnClickListener {
            navController.navigate(R.id.mainFragment)
            dismiss()
        }

        mViewModel.buyState.observe(viewLifecycleOwner, {
            render(it)
        })
    }

    private fun render(state: ViewState) {
        when (state) {
            is ViewState.Success -> {
                mMainContainer.visibility = View.GONE
                mSuccessBuyingContainer.visibility = View.VISIBLE
            }
            is ViewState.ConnectError -> {
                mMainContainer.visibility = View.VISIBLE
                mSuccessBuyingContainer.visibility = View.GONE
                Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupUI(rootView: View) {
        mMainContainer = rootView.findViewById(R.id.buy_asset_main_container)
        mSumField = rootView.findViewById(R.id.buy_asset_sum_et)
        mBuyBtn = rootView.findViewById(R.id.buy_asset_btn)
        mSuccessBuyingContainer = rootView.findViewById(R.id.success_buying_asset_container)
        mNavigateToMainScreenBtn = rootView.findViewById(R.id.buy_asset_navigate_to_main_screen)
    }
}
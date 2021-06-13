package ru.iskhakoff.rarible_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.iskhakoff.rarible_app.R

class SuccessCreatingProjectBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var mGoToMainScreenBtn: Button
    private lateinit var navController: NavController

    companion object {
        fun newInstance(): SuccessCreatingProjectBottomSheetFragment{
            return SuccessCreatingProjectBottomSheetFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.success_creating_project_fragment, container, false)
        setupUI(rootView)
        activity?.let {
            navController = it.findNavController(R.id.fragment_container)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mGoToMainScreenBtn.setOnClickListener {
            navController.navigate(R.id.mainFragment)
            dismiss()
        }
    }

    private fun setupUI(rootView: View) {
        mGoToMainScreenBtn = rootView.findViewById(R.id.navigate_to_main_screen)
    }
}
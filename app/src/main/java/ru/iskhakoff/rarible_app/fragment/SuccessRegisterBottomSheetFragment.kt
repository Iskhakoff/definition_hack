package ru.iskhakoff.rarible_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.iskhakoff.rarible_app.R

class SuccessRegisterBottomSheetFragment(private val callback: ISuccessRegister) : BottomSheetDialogFragment() {

    private lateinit var startBtn : Button

    companion object {
        fun newInstance(callback: ISuccessRegister):
                SuccessRegisterBottomSheetFragment = SuccessRegisterBottomSheetFragment(callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.success_register_fragment, container, false)
        setupUI(rootView)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startBtn.setOnClickListener {
            callback.navigateToMainScreen()
            dismiss()
        }
    }

    interface ISuccessRegister {
        fun navigateToMainScreen()
    }

    private fun setupUI(rootView: View) {
        startBtn = rootView.findViewById(R.id.start_using_app_btn)
    }
}
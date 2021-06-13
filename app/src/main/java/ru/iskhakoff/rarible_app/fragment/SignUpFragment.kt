package ru.iskhakoff.rarible_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStructure
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.iskhakoff.domain.ApiRepository
import ru.iskhakoff.domain.PrefsRepository
import ru.iskhakoff.rarible_app.R
import ru.iskhakoff.rarible_app.helper.AuthViewModelFactory
import ru.iskhakoff.rarible_app.utils.ViewState
import ru.iskhakoff.rarible_app.viewmodel.AuthViewModel

class SignUpFragment(private val callback: IClickRegisterBtn, private val apiRepository: ApiRepository, private val prefsRepository: PrefsRepository) : Fragment() {

    private lateinit var mLoginField: EditText
    private lateinit var mEmailField: EditText
    private lateinit var mPasswordField: EditText
    private lateinit var mConfirmPasswordField: EditText
    private lateinit var mErrorLabelPasswordMismatch: TextView
    private lateinit var mErrorFillAllFields: TextView
    private lateinit var mErrorLoginExisting: TextView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mSignUpBtn: Button

    private lateinit var mViewModel: AuthViewModel

    companion object {
        fun newInstance(callback: IClickRegisterBtn,
                        apiRepository: ApiRepository,
                        prefsRepository: PrefsRepository)
        : SignUpFragment = SignUpFragment(callback, apiRepository, prefsRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this, AuthViewModelFactory(apiRepository, prefsRepository)).get(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_signup, container, false)
        setupUI(rootView)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.stateSignUp.observe(viewLifecycleOwner, {
            render(it)
        })

        mSignUpBtn.setOnClickListener {
            mViewModel.signUp(
                mLoginField.text.toString(),
                mEmailField.text.toString(),
                mPasswordField.text.toString(),
                mConfirmPasswordField.text.toString()
            )
        }
    }

    private fun render(state: ViewState) {
        when (state) {
            is ViewState.Loading -> {
                mSignUpBtn.visibility = View.INVISIBLE
                mProgressBar.visibility = View.VISIBLE
                mErrorLabelPasswordMismatch.visibility = View.INVISIBLE
                mErrorFillAllFields.visibility = View.INVISIBLE
                mErrorLoginExisting.visibility = View.INVISIBLE
            }
            is ViewState.ErrorPasswordMismatch -> {
                mSignUpBtn.visibility = View.VISIBLE
                mProgressBar.visibility = View.INVISIBLE
                mErrorLabelPasswordMismatch.visibility = View.VISIBLE
                mErrorFillAllFields.visibility = View.INVISIBLE
                mErrorLoginExisting.visibility = View.INVISIBLE
            }
            is ViewState.ErrorNotFilledFields -> {
                mSignUpBtn.visibility = View.VISIBLE
                mProgressBar.visibility = View.INVISIBLE
                mErrorLabelPasswordMismatch.visibility = View.INVISIBLE
                mErrorFillAllFields.visibility = View.VISIBLE
                mErrorLoginExisting.visibility = View.INVISIBLE
            }
            is ViewState.ErrorLoginAlreadyExists -> {
                mSignUpBtn.visibility = View.VISIBLE
                mProgressBar.visibility = View.INVISIBLE
                mErrorLabelPasswordMismatch.visibility = View.INVISIBLE
                mErrorFillAllFields.visibility = View.INVISIBLE
                mErrorLoginExisting.visibility = View.VISIBLE
            }
            is ViewState.ConnectError -> {

            }
            is ViewState.RemoteError -> {

            }
            is ViewState.Success -> {
                mSignUpBtn.visibility = View.VISIBLE
                mProgressBar.visibility = View.INVISIBLE
                mErrorLabelPasswordMismatch.visibility = View.INVISIBLE
                mErrorFillAllFields.visibility = View.INVISIBLE
                mErrorLoginExisting.visibility = View.INVISIBLE
                callback.register()
            }
        }
    }

    interface IClickRegisterBtn {
        fun register()
    }

    private fun setupUI(rootView: View) {
        mLoginField = rootView.findViewById(R.id.sign_up_login_et)
        mEmailField = rootView.findViewById(R.id.sign_up_user_email_et)
        mPasswordField = rootView.findViewById(R.id.sign_up_user_password_et)
        mConfirmPasswordField = rootView.findViewById(R.id.sign_up_user_confirm_password)
        mErrorLabelPasswordMismatch = rootView.findViewById(R.id.error_password_mismatch_sign_up)
        mErrorFillAllFields = rootView.findViewById(R.id.error_message_fill_all_fields_sign_up)
        mErrorLoginExisting = rootView.findViewById(R.id.error_login_existing_sign_up)
        mProgressBar = rootView.findViewById(R.id.sign_up_fragment_progress_bar)
        mSignUpBtn = rootView.findViewById(R.id.user_register_btn)
    }
}
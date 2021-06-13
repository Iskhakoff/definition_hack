package ru.iskhakoff.rarible_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class SignInFragment(private val callback: IClickLoginBtn, private val apiRepository: ApiRepository, private val prefsRepository: PrefsRepository) : Fragment() {

    private lateinit var mLoginField: EditText
    private lateinit var mPassword: EditText
    private lateinit var mErrorInvalidDataMessage: TextView
    private lateinit var mErrorFillAllFieldsMessage: TextView
    private lateinit var mSignInBtn: Button
    private lateinit var mProgressBar: ProgressBar

    private lateinit var mViewModel: AuthViewModel

    companion object {
        fun newInstance(
            callback: IClickLoginBtn,
            apiRepository: ApiRepository,
            prefsRepository: PrefsRepository
        ): SignInFragment = SignInFragment(callback, apiRepository, prefsRepository)
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
        val rootView = inflater.inflate(R.layout.fragment_signin, container, false)
        setupUI(rootView)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.stateSignIn.observe(viewLifecycleOwner, {
            render(it)
        })

        mSignInBtn.setOnClickListener {
            mViewModel.signIn(mLoginField.text.toString(), mPassword.text.toString())
        }
    }

    private fun render(state: ViewState) {
        when (state) {
            is ViewState.Loading -> {
                mSignInBtn.visibility = View.INVISIBLE
                mProgressBar.visibility = View.VISIBLE
                mErrorFillAllFieldsMessage.visibility = View.INVISIBLE
                mErrorInvalidDataMessage.visibility = View.INVISIBLE
                mLoginField.isEnabled = false
                mPassword.isEnabled = false
            }
            is ViewState.ErrorNotFilledFields -> {
                mErrorFillAllFieldsMessage.visibility = View.VISIBLE
                mErrorInvalidDataMessage.visibility = View.INVISIBLE
                mProgressBar.visibility = View.GONE
                mSignInBtn.visibility = View.VISIBLE
                mLoginField.isEnabled = true
                mPassword.isEnabled = true
            }
            is ViewState.ErrorInvalidData -> {
                mErrorInvalidDataMessage.visibility = View.VISIBLE
                mErrorFillAllFieldsMessage.visibility = View.INVISIBLE
                mProgressBar.visibility = View.GONE
                mSignInBtn.visibility = View.VISIBLE
                mLoginField.isEnabled = true
                mPassword.isEnabled = true
            }
            is ViewState.RemoteError -> {

            }
            is ViewState.ConnectError -> {

            }
            is ViewState.Success -> {
                mErrorInvalidDataMessage.visibility = View.INVISIBLE
                mErrorFillAllFieldsMessage.visibility = View.INVISIBLE
                mProgressBar.visibility = View.GONE
                mSignInBtn.visibility = View.VISIBLE
                mLoginField.isEnabled = true
                mPassword.isEnabled = true
                callback.login()
            }
        }
    }

    interface IClickLoginBtn {
        fun login()
    }

    private fun setupUI(rootView: View) {
        mLoginField = rootView.findViewById(R.id.user_login_et)
        mPassword = rootView.findViewById(R.id.user_password_et)
        mErrorInvalidDataMessage = rootView.findViewById(R.id.error_message_incorrect_data_sign_in)
        mErrorFillAllFieldsMessage = rootView.findViewById(R.id.error_message_fill_all_fields_sign_in)
        mSignInBtn = rootView.findViewById(R.id.user_login_btn)
        mProgressBar = rootView.findViewById(R.id.sign_in_fragment_progress_bar)
    }
}
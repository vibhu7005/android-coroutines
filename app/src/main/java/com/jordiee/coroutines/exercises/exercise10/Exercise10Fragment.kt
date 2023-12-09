package com.jordiee.coroutines.exercises.exercise10

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jordiee.coroutines.R
import com.jordiee.coroutines.demonstrations.uncaughtexception.LoggedInUser
import com.jordiee.coroutines.demonstrations.uncaughtexception.LoginUseCaseUncaughtException
import com.jordiee.coroutines.home.ScreenReachableFromHome
import kotlinx.coroutines.*
import java.lang.Exception

class Exercise10Fragment : com.jordiee.coroutines.common.BaseFragment() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    override val screenTitle get() = ScreenReachableFromHome.EXERCISE_10.description

    private lateinit var loginUseCase: LoginUseCaseUncaughtException

    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginUseCase = compositionRoot.loginUseCaseUncaughtException
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        view.apply {
            edtUsername = findViewById(R.id.edt_username)
            edtPassword = findViewById(R.id.edt_password)
            btnLogin = findViewById(R.id.btn_login)
        }

        refreshUiState()

        edtUsername.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                refreshUiState()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        edtPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                refreshUiState()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        btnLogin.setOnClickListener {
                coroutineScope.launch {
                    try {
                        btnLogin.isEnabled = false
                        val result = loginUseCase.logIn(getUsername(), getPassword())
                        when (result) {
                            is LoginUseCaseUncaughtException.Result.Success -> onUserLoggedIn(result.user)
                            is LoginUseCaseUncaughtException.Result.InvalidCredentials -> onInvalidCredentials()
                            is LoginUseCaseUncaughtException.Result.GeneralError -> onGeneralError()
                        }
                    } finally {
                        refreshUiState()
                    }
                }
        }

        return view
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun refreshUiState() {
        val username = getUsername()
        val password = getPassword()
        btnLogin.isEnabled = username.isNotEmpty() && password.isNotEmpty()
    }

    private fun getUsername(): String {
        return edtUsername.text.toString()
    }

    private fun getPassword(): String {
        return edtPassword.text.toString()
    }

    private fun onUserLoggedIn(user: LoggedInUser) {
        Toast.makeText(requireContext(), "successful login", Toast.LENGTH_SHORT).show()
    }

    private fun onInvalidCredentials() {
        Toast.makeText(requireContext(), "invalid credentials", Toast.LENGTH_SHORT).show()
    }

    private fun onGeneralError() {
        Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(): Fragment {
            return Exercise10Fragment()
        }
    }
}
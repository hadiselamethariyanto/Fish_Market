package com.example.fishmarket.ui.login

import android.content.Intent
import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.inputmethod.EditorInfo
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.ActivityLoginBinding
import com.example.fishmarket.domain.model.User
import com.example.fishmarket.ui.main.MainActivity
import com.example.fishmarket.utilis.Utils
import com.example.fishmarket.utilis.Utils.afterTextChanged
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val login = binding.login

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })


        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        ).observe(this@LoginActivity, userObserver)
                }
                false
            }

            login.setOnClickListener {
                loginViewModel.login(username.text.toString(), password.text.toString())
                    .observe(this@LoginActivity, userObserver)
            }
        }
    }

    private val userObserver = Observer<Resource<User>> { res ->
        when (res) {
            is Resource.Loading -> {
                binding.login.isEnabled = false
            }
            is Resource.Success -> {
                binding.login.isEnabled = true
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
            is Resource.Error -> {
                binding.login.isEnabled = true
                Utils.showMessage(this, res.message.toString())
            }
        }
    }


}

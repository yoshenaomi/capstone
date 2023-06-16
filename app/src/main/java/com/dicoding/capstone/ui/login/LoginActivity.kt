package com.dicoding.capstone.ui.login

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.capstone.ui.bottomnav.BottomNavbarActivity
import com.dicoding.capstone.R
import com.dicoding.capstone.data.local.PrefDataStore
import com.dicoding.capstone.data.local.dataStore
import com.dicoding.capstone.databinding.ActivityLoginBinding
import com.dicoding.capstone.ui.register.RegisterActivity
import com.dicoding.capstone.utils.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "User")

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()

        binding.registerButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupAction() {
        binding.apply {
            signInButton.setOnClickListener {
                val email = emailLoginEditText.text.toString().trim()
                val password = passwordLoginEditText.text.toString().trim()
                when {
                    email.isEmpty() -> {
                        emailLoginEditText.error = getString(R.string.email_empty)
                    }

                    password.isEmpty() -> {
                        passwordLoginEditText.error = getString(R.string.password_empty)
                    }

                    else -> {
                        loginViewModel.login(email, password)
                    }
                }
            }
        }
    }


    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(PrefDataStore.getInstance(dataStore))
        )[LoginViewModel::class.java]



        loginViewModel.let { viewModel ->
            viewModel.loginResult.observe(this) { login ->
                viewModel.saveUser(
                    login.result.userID,
                    login.result.email,
                    login.result.token,
                    login.result.username
                )
            }


            viewModel.responseMessage.observe(this) { response ->
                if (response == "400") {
                    Toast.makeText(this, getString(R.string.api_bad_req), Toast.LENGTH_SHORT).show()
                } else if (response == "created") {
                    Toast.makeText(this, getString(R.string.api_created), Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, BottomNavbarActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
            }
            viewModel.isLoading.observe(this) { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

}
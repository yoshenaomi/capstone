package com.dicoding.capstone.ui.register

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.capstone.R
import com.dicoding.capstone.data.local.PrefDataStore
import com.dicoding.capstone.data.local.dataStore
import com.dicoding.capstone.databinding.ActivityRegisterBinding
import com.dicoding.capstone.ui.login.LoginActivity
import com.dicoding.capstone.utils.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var regisViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        setUpViewModel()
        setupView()
    }

    private fun setupAction() {
        binding.apply {
            signInButton.setOnClickListener {
                val email = emailRegisEditText.text.toString().trim()
                val password = passwordRegisEditText.text.toString().trim()
                val userName = usernameRegisEditText.text.toString().trim()
                when {
                    email.isEmpty() -> {
                        emailRegisEditText.error = getString(R.string.email_empty)
                    }

                    password.isEmpty() -> {
                        passwordRegisEditText.error = getString(R.string.password_empty)
                    }

                    userName.isEmpty() -> {
                        usernameRegisEditText.error = getString(R.string.username_empty)
                    }

                    else -> {
                        regisViewModel.register(userName, email, password)
                    }
                }
            }
        }
    }

    private fun setUpViewModel() {
        regisViewModel = ViewModelProvider(
            this,
            ViewModelFactory(PrefDataStore.getInstance(dataStore))
        )[RegisterViewModel::class.java]

        regisViewModel.let { viewModels ->
            viewModels.responseMessage.observe(this) { response ->
                if (response == "400") {
                    Toast.makeText(this, getString(R.string.api_bad_req), Toast.LENGTH_SHORT).show()
                } else if (response == "created") {
                    Toast.makeText(this, getString(R.string.api_created), Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            viewModels.isLoading.observe(this) { isLoading ->
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
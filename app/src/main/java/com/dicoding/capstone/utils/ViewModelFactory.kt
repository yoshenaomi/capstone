package com.dicoding.capstone.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.capstone.data.local.PrefDataStore
import com.dicoding.capstone.ui.login.LoginViewModel

import com.dicoding.capstone.ui.register.RegisterViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val pref: PrefDataStore) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(pref) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel Class : " + modelClass.name)
        }
    }

}
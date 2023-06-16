package com.dicoding.capstone.ui.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.capstone.data.local.PrefDataStore
import com.dicoding.capstone.data.network.retrofit.ApiConfig
import com.dicoding.capstone.model.LoginBody
import com.dicoding.capstone.model.LoginModel
import com.dicoding.capstone.model.Result
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: PrefDataStore) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    val responseMessage = MutableLiveData("")
    private val tag = LoginViewModel::class.java.simpleName

    val loginResult = MutableLiveData<LoginModel>()

    fun getUser(): LiveData<Result> {
        return pref.getUser().asLiveData()
    }

    fun saveUser(userId: String, email: String, token: String, userName: String) {
        viewModelScope.launch {
            pref.saveUser(userId, email, token, userName)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            pref.signOut()
        }
    }

    fun login(email: String, password: String) {
        _isLoading.value = true
        val user = LoginBody(email, password)
        val client = ApiConfig.getApiService().login(user)
        client.enqueue(object : Callback<LoginModel> {
            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                when (response.code()) {
                    400 -> responseMessage.postValue("Bad Req")
                    200 -> {
                        loginResult.postValue(response.body())
                        responseMessage.postValue("created")
                    }

                    else -> responseMessage.postValue("ERROR  ${response.code()} + ${response.message()}")
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<LoginModel>, t: Throwable) {

                _isLoading.value = true
                Log.e(tag,"OnFailure : ${t.message}")
                responseMessage.postValue(t.message)
            }

        })
    }
}
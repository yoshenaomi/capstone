package com.dicoding.capstone.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.capstone.data.local.PrefDataStore
import com.dicoding.capstone.data.network.retrofit.ApiConfig
import com.dicoding.capstone.model.RegisBody
import com.dicoding.capstone.model.RegisterModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val pref: PrefDataStore) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val responseMessage = MutableLiveData("")
    private val tag = RegisterViewModel::class.java.simpleName

    fun register(userName: String, email: String, password: String) {
        _isLoading.value = true
        val user = RegisBody(userName, email, password)
        val client = ApiConfig.getApiService().regis(user)


        client.enqueue(object : Callback<RegisterModel> {
            override fun onResponse(call: Call<RegisterModel>, response: Response<RegisterModel>) {
                when (response.code()) {
                    400 -> responseMessage.postValue("Bad Req")
                    200 -> responseMessage.postValue("created")
                    else -> responseMessage.postValue("ERROR ${response.code()} : ${response.errorBody()}")
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<RegisterModel>, t: Throwable) {
                _isLoading.value = true
                Log.e(tag, "onFailure : ${t.message}")
                responseMessage.postValue(t.message)
            }
        })
    }

}
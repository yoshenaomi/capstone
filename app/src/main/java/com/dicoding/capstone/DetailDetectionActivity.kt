package com.dicoding.capstone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.dicoding.capstone.data.local.PrefDataStore
import com.dicoding.capstone.data.local.dataStore
import com.dicoding.capstone.ui.login.LoginViewModel
import com.dicoding.capstone.utils.ViewModelFactory

class DetailDetectionActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_detection)

    }
}
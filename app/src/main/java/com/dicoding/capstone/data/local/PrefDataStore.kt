package com.dicoding.capstone.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.capstone.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("User")

class PrefDataStore private constructor(private val datastore: DataStore<Preferences>) {

    fun getToken(): Flow<String> = datastore.data.map {
        it[TOKEN_KEY] ?: "Unregulated"
    }

    suspend fun saveUser(userId: String, email: String, token: String, userName: String) {
        datastore.edit { preferences ->
            preferences[USERID_KEY] = userId
            preferences[EMAIL_KEY] = email
            preferences[TOKEN_KEY] = token
            preferences[USERNAME_KEY] = userName
        }
    }

    fun getUser(): Flow<Result> {
        return datastore.data.map { preferences ->
            Result(
                preferences[USERID_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[USERNAME_KEY] ?: "",
            )
        }
    }

    suspend fun signOut() {
        datastore.edit { preferences ->
            preferences[USERID_KEY] = ""
            preferences[EMAIL_KEY] = ""
            preferences[TOKEN_KEY] = ""
            preferences[USERNAME_KEY] = ""
        }
    }

    companion object {
        @Volatile
        private var INSTANCE : PrefDataStore? = null

        private val TOKEN_KEY = stringPreferencesKey("token")
        private val USERID_KEY = stringPreferencesKey("userId")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val EMAIL_KEY = stringPreferencesKey("email")

        fun getInstance(datastore: DataStore<Preferences>) : PrefDataStore{
            return INSTANCE ?: synchronized(this){
                val instance = PrefDataStore(datastore)
                INSTANCE = instance
                instance
            }
        }
    }
}
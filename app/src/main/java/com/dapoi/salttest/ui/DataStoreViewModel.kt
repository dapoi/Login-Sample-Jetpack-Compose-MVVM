package com.dapoi.salttest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dapoi.salttest.data.pref.DataPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val dataPreference: DataPreference
) : ViewModel() {

    fun isLogin(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataPreference.saveIsLoggedIn(state)
        }
    }

    fun saveToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) { dataPreference.saveUserToken(token) }
    }

    fun saveEmail(email: String) {
        viewModelScope.launch(Dispatchers.IO) { dataPreference.saveUserEmail(email) }
    }

    fun clear() {
        viewModelScope.launch(Dispatchers.IO) { dataPreference.clear() }
    }

    val isLoggedIn = dataPreference.isLoggedIn.asLiveData()
    val getToken = dataPreference.userToken
    val getEmail = dataPreference.userEmail
}
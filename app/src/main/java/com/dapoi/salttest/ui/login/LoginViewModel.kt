package com.dapoi.salttest.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dapoi.salttest.data.LoginRepository
import com.dapoi.salttest.data.model.LoginRequest
import com.dapoi.salttest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> get() = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginRepository.login(LoginRequest(email, password)).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        _loginState.value = LoginState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _loginState.value = LoginState(loginResponse = response.data!!)
                    }
                    is Resource.Error -> {
                        _loginState.value = LoginState(error = response.message!!)
                    }
                }
            }
        }
    }
}
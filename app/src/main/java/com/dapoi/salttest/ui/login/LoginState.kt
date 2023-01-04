package com.dapoi.salttest.ui.login

import com.dapoi.salttest.data.model.LoginResponse

data class LoginState(
    val isLoading: Boolean = false,
    val loginResponse: LoginResponse = LoginResponse(),
    val error: String = ""
)
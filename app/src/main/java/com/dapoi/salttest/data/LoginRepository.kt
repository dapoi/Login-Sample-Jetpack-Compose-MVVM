package com.dapoi.salttest.data

import com.dapoi.salttest.data.model.LoginRequest
import com.dapoi.salttest.data.model.LoginResponse
import com.dapoi.salttest.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    fun login(loginRequest: LoginRequest): Flow<Resource<LoginResponse>>
}
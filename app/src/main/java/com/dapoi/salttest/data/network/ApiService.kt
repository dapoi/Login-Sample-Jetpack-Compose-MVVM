package com.dapoi.salttest.data.network

import com.dapoi.salttest.data.model.LoginRequest
import com.dapoi.salttest.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/api/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse
}
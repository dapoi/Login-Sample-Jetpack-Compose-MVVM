package com.dapoi.salttest.data

import com.dapoi.salttest.data.model.LoginRequest
import com.dapoi.salttest.data.model.LoginResponse
import com.dapoi.salttest.data.network.ApiService
import com.dapoi.salttest.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : LoginRepository {

    override fun login(loginRequest: LoginRequest): Flow<Resource<LoginResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = apiService.login(loginRequest)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            }
        }
    }
}
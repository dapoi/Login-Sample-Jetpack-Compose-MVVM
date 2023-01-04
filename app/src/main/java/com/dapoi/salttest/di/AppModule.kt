package com.dapoi.salttest.di

import android.content.Context
import com.dapoi.salttest.data.LoginRepository
import com.dapoi.salttest.data.LoginRepositoryImpl
import com.dapoi.salttest.data.network.ApiService
import com.dapoi.salttest.data.pref.DataPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create API service
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(apiService: ApiService): LoginRepository {
       return LoginRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataPreference {
        return DataPreference(context)
    }
}
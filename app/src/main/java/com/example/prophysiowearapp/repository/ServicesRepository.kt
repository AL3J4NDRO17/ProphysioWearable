package com.example.prophysiowearapp.repository

import com.example.prophysiowearapp.network.ApiService
import com.example.prophysiowearapp.network.ServiceDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServicesRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getServices(): Result<List<ServiceDto>> {
        return try {
            val response = apiService.getServices()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch services: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

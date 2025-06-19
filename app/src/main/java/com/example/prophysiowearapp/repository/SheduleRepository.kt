package com.example.prophysiowearapp.repository

import com.example.prophysiowearapp.network.ApiService
import com.example.prophysiowearapp.network.ScheduleDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScheduleRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getAvailableSchedules(): Result<List<ScheduleDto>> {
        return try {
            val response = apiService.getAvailableSchedules()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch schedules: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

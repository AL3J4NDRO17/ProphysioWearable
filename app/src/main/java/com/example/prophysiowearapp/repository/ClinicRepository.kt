package com.example.prophysiowearapp.repository

import com.example.prophysiowearapp.network.ApiService
import com.example.prophysiowearapp.network.ClinicInfoResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClinicRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getClinicInfo(): Result<ClinicInfoResponse> {
        return try {
            val response = apiService.getClinicInfo()
            if (response.isSuccessful && response.body() != null) {
                val companiesResponse = response.body()!!

                // Tomar la primera empresa del array
                val company = companiesResponse.companies.firstOrNull()

                if (company != null) {
                    // Procesar los datos para extraer los identificadores de las URLs
                    val clinicInfo = ClinicInfoResponse(
                        name = "ProPhysio", // Puedes agregar este campo a tu API si lo necesitas
                        address = company.address,
                        latitude = company.latitude,
                        longitude = company.longitude,
                        whatsappPhone = company.phone,
                        instagramUser = extractInstagramUsername(company.instagram),
                        facebookPageId = extractFacebookPageId(company.facebook)
                    )
                    Result.success(clinicInfo)
                } else {
                    Result.failure(Exception("No company data found"))
                }
            } else {
                Result.failure(Exception("Failed to fetch clinic info: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun extractInstagramUsername(instagramUrl: String?): String? {
        if (instagramUrl.isNullOrBlank()) return null

        // Extraer username de URLs como: https://www.instagram.com/prophysio_huejutla/
        val regex = Regex("instagram\\.com/([^/?]+)")
        val match = regex.find(instagramUrl)
        return match?.groupValues?.get(1)?.removeSuffix("/")
    }

    private fun extractFacebookPageId(facebookUrl: String?): String? {
        if (facebookUrl.isNullOrBlank()) return null

        // Extraer page ID de URLs como: https://facebook.com/empresa
        val regex = Regex("facebook\\.com/([^/?]+)")
        val match = regex.find(facebookUrl)
        return match?.groupValues?.get(1)?.removeSuffix("/")
    }
}

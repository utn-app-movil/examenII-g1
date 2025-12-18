package Service

import model.pablo_ApiDataResponse
import model.pablo_LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface pablo_ApiContainers {
    @POST("/users/auth")
    suspend fun login(@Body loginRequest: pablo_LoginRequest): pablo_ApiDataResponse
}
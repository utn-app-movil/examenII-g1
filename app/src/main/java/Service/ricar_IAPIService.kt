package Service

import cr.ac.utn.appmovil.containers.*
import model.ricar_AssignRequest
import model.ricar_AuthRequest
import model.ricar_AuthResponse
import model.ricar_ReleaseRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ricar_IAPIService {
    @POST("/users/auth")
    suspend fun authenticateUser(@Body authRequest: ricar_AuthRequest): Response<ricar_AuthResponse>

    @GET("/technicians")
    suspend fun getTechnicians(): Response<ricar_TechniciansResponse>

    @GET("/containers")
    suspend fun getContainers(): Response<ricar_ContainersResponse>

    @PUT("/containers/asign")
    suspend fun assignContainer(@Body assignRequest: ricar_AssignRequest): Response<Void> // Assuming no specific response body

    @PUT("/containers/release")
    suspend fun releaseContainer(@Body releaseRequest: ricar_ReleaseRequest): Response<Void> // Assuming no specific response body
}
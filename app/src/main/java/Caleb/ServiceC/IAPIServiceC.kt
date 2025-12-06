package Caleb.ServiceC

import Caleb.modelC.AuthRequest
import Caleb.modelC.AuthResponse
import Caleb.modelC.ContainerCreateRequest
import Caleb.modelC.ContainerCreateResponse
import Caleb.modelC.ContainersListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IAPIServiceC {
    @POST("/users/auth")
    suspend fun authenticate(@Body body: AuthRequest): Response<AuthResponse>

    @POST("/containers")
    suspend fun createContainer(@Body body: ContainerCreateRequest): Response<ContainerCreateResponse>

    @GET("/containers")
    suspend fun getContainers(): Response<ContainersListResponse>
}
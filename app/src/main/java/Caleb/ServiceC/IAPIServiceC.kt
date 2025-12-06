package Caleb.ServiceC

import Caleb.modelC.AuthRequest
import Caleb.modelC.AuthResponse
import Caleb.modelC.ContainerCreateRequest
import Caleb.modelC.ContainerCreateResponse
import Caleb.modelC.ContainersListResponse
import Caleb.modelC.ContainerAssignRequest
import Caleb.modelC.ContainerReleaseRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface IAPIServiceC {
    @POST("/users/auth")
    suspend fun authenticate(@Body body: AuthRequest): Response<AuthResponse>

    @POST("/containers")
    suspend fun createContainer(@Body body: ContainerCreateRequest): Response<ContainerCreateResponse>

    @GET("/containers")
    suspend fun getContainers(): Response<ContainersListResponse>

    @PUT("/containers/asign")
    suspend fun assignContainer(@Body body: ContainerAssignRequest): Response<ContainerCreateResponse>

    @PUT("/containers/release")
    suspend fun releaseContainer(@Body body: ContainerReleaseRequest): Response<ContainerCreateResponse>
}
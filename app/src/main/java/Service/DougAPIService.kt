package Service

import model.*
import retrofit2.Response
import retrofit2.http.*

interface DougAPIService {

    @POST("/users/auth")
    suspend fun authenticate(@Body request: DougAuthRequest): Response<DougAuthResponse>

    @GET("/containers")
    suspend fun getContainers(): Response<DougContainerListResponse>

    @POST("/containers")
    suspend fun createContainer(@Body request: DougContainerRequest): Response<DougContainerResponse>

    @PUT("/containers/asign")
    suspend fun assignContainer(@Body request: DougAssignRequest): Response<DougContainerResponse>

    @PUT("/containers/release")
    suspend fun releaseContainer(@Body request: DougReleaseRequest): Response<DougContainerResponse>
}
package Service

import cr.ac.utn.appmovil.containers.bray_AssignContainerRequest
import cr.ac.utn.appmovil.containers.bray_ContainerListResponse
import cr.ac.utn.appmovil.containers.bray_ContainerRequest
import cr.ac.utn.appmovil.containers.bray_ContainerResponse
import cr.ac.utn.appmovil.containers.bray_GenericResponse
import cr.ac.utn.appmovil.containers.bray_LoginRequest
import cr.ac.utn.appmovil.containers.bray_LoginResponse
import cr.ac.utn.appmovil.containers.bray_ReleaseContainerRequest
import cr.ac.utn.appmovil.containers.bray_Technician
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface bray_IAPIService {
    @POST("/users/auth")
    suspend fun login(@Body request: bray_LoginRequest): Response<bray_LoginResponse>

    @GET("/technicians/")
    suspend fun getTechnicians(): Response<List<bray_Technician>>

    @POST("/containers")
    suspend fun createContainer(@Body request: bray_ContainerRequest): Response<bray_ContainerResponse>

    @GET("/containers")
    suspend fun getContainers(): Response<bray_ContainerListResponse>

    @PUT("/containers/asign")
    suspend fun assignContainer(@Body request: bray_AssignContainerRequest): Response<bray_GenericResponse>

    @PUT("/containers/release")
    suspend fun releaseContainer(@Body request: bray_ReleaseContainerRequest): Response<bray_GenericResponse>
}
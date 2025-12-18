package cr.ac.utn.appmovil.containers

import cr.ac.utn.appmovil.containers.models.bran_CreateContainerRequest
import cr.ac.utn.appmovil.containers.models.BranLoginRequest
import cr.ac.utn.appmovil.containers.models.BranLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface bran_ApiService {
    @POST("users/auth")
    suspend fun login(@Body request: BranLoginRequest): Response<BranLoginResponse>

    @GET("containers")
    suspend fun getContainers(): Response<ContainerResponse>

    @PUT("containers/asign")
    suspend fun assignContainer(@Body request: AssignRequest): Response<Unit>

    @PUT("containers/release")
    suspend fun releaseContainer(@Body request: ReleaseRequest): Response<Unit>

    @POST("containers")
    suspend fun createContainer(@Body request: bran_CreateContainerRequest): Response<Unit>
}

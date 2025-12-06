package Service

import cr.ac.utn.appmovil.containers.model.kam_ApiResponse
import cr.ac.utn.appmovil.containers.model.kam_Container
import cr.ac.utn.appmovil.containers.model.kam_Technician
import cr.ac.utn.appmovil.containers.model.kam_User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface kam_ApiService {

    @GET("/technicians")
    suspend fun getTechnicians(): Response<kam_ApiResponse<List<kam_Technician>>>

    @POST("/users/auth")
    suspend fun authenticateUser(@Body credentials: Map<String, String>): Response<kam_ApiResponse<kam_User>>

    @POST("/containers")
    suspend fun createContainer(@Body container: Map<String, String>): Response<kam_ApiResponse<kam_Container>>

    @GET("/containers")
    suspend fun getContainers(): Response<kam_ApiResponse<List<kam_Container>>>

    @PUT("/containers/asign")
    suspend fun assignContainer(@Body data: Map<String, String>): Response<kam_ApiResponse<kam_Container>>

    @PUT("/containers/release")
    suspend fun releaseContainer(@Body data: Map<String, String>): Response<kam_ApiResponse<kam_Container>>
}
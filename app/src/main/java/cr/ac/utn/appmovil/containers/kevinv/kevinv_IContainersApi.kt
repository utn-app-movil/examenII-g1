package cr.ac.utn.appmovil.containers.kevinv

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface kevinv_IContainersApi {

    @POST("/users/auth")
    suspend fun kevinv_authenticate(
        @Body body: kevinv_AuthRequest
    ): kevinv_ApiResponse<kevinv_AuthUserData>

    @GET("/technicians")
    suspend fun kevinv_getTechnicians(
    ): kevinv_ApiResponse<List<kevinv_Technician>>

    @GET("/containers")
    suspend fun kevinv_getContainers(
    ): kevinv_ApiResponse<List<kevinv_Container>>

    @POST("/containers")
    suspend fun kevinv_createContainer(
        @Body body: kevinv_CreateContainerRequest
    ): kevinv_ApiResponse<kevinv_Container>

    @PUT("/containers/asign")
    suspend fun kevinv_assignContainer(
        @Body body: kevinv_AssignContainerRequest
    ): kevinv_ApiResponse<kevinv_Container>

    @PUT("/containers/release")
    suspend fun kevinv_releaseContainer(
        @Body body: kevinv_ReleaseContainerRequest
    ): kevinv_ApiResponse<kevinv_Container>
}

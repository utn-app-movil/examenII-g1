package Service

import model.sam_ApiResponse
import model.sam_AssignContainerRequest
import model.sam_Container
import model.sam_CreateContainerRequest
import model.sam_LoginRequest
import model.sam_ReleaseContainerRequest
import model.sam_Technician
import model.sam_UserData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

// API endpoints

interface sam_IAPIService {

    @POST("users/auth")
    suspend fun authUser(
        @Body request: sam_LoginRequest
    ): Response<sam_ApiResponse<sam_UserData>>

    @GET("technicians")
    suspend fun getTechnicians(
    ): Response<sam_ApiResponse<List<sam_Technician>>>

    @POST("containers")
    suspend fun createContainer(
        @Body request: sam_CreateContainerRequest
    ): Response<sam_ApiResponse<sam_Container>>

    @GET("containers")
    suspend fun getContainers():
            Response<sam_ApiResponse<List<sam_Container>>>

    @PUT("containers/asign")
    suspend fun assignContainer(
        @Body request: sam_AssignContainerRequest
    ): Response<sam_ApiResponse<sam_Container>>

    @PUT("containers/release")
    suspend fun releaseContainer(
        @Body request: sam_ReleaseContainerRequest
    ): Response<sam_ApiResponse<sam_Container>>
}

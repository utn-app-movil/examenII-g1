package Service

import model.*
import retrofit2.http.*

interface pau_IAPIService {

    @POST("users/auth")
    suspend fun pau_authenticateUser(@Body request: pau_LoginRequest): pau_UserResponse


    @GET("containers")
    suspend fun pau_getContainers(): pau_ContainerListResponse

    @POST("containers")
    suspend fun pau_createContainer(@Body request: pau_CreateContainerRequest): pau_BaseResponse

    @PUT("containers/asign")
    suspend fun pau_assignContainer(@Body request: pau_AssignRequest): pau_BaseResponse

    @PUT("containers/release")
    suspend fun pau_releaseContainer(@Body request: pau_ReleaseRequest): pau_BaseResponse
}
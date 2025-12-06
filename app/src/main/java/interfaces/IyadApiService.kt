package interfaces


import model.*
import retrofit2.http.*

interface IyadApiService {

    @POST("users/auth")
    suspend fun authenticateUser(@Body request: yadAuthRequest): yadApiResponse<yadUser>

    @POST("containers")
    suspend fun createContainer(@Body container: yadContainer): yadApiResponse<yadContainer>

    @GET("containers")
    suspend fun getContainers(): yadApiResponse<List<yadContainer>>

    @PUT("containers/asign")
    suspend fun assignContainer(@Body request: yadAssignRequest): yadApiResponse<yadContainer>

    @PUT("containers/release")
    suspend fun releaseContainer(@Body request: yadReleaseRequest): yadApiResponse<yadContainer>


    @GET("technicians")
    suspend fun getTechnicians(): yadApiResponse<List<yadUser>>
}
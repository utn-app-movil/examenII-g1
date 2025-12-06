package Service

import model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface JohelIAPIService {

    // LOGIN: id + password
    @POST("users/auth")
    fun authUser(
        @Body body: JohelAuthRequest
    ): Call<JohelBaseResponse<JohelTechnician>>

    @GET("technicians/")
    fun getTechnicians(): Call<JohelBaseResponse<List<JohelTechnician>>>


    @POST("containers")
    fun createContainer(
        @Body body: JohelContainerRequest
    ): Call<JohelBaseResponse<JohelContainer>>

    @GET("containers")
    fun getContainers(): Call<JohelBaseResponse<List<JohelContainer>>>

    @PUT("containers/asign")
    fun assignContainer(
        @Body body: JohelAssignRequest
    ): Call<JohelBaseResponse<JohelContainer>>

    @PUT("containers/release")
    fun releaseContainer(
        @Body body: JohelReleaseRequest
    ): Call<JohelBaseResponse<JohelContainer>>
}

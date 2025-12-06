package Service

import model.luis_Loginrequest
import model.luis_Loginresponse
import model.luis_CreateContainerRequest
import model.luis_CreateContainerResponse
import model.luis_GetContainersResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface luis_IAPIService {

    @POST("/users/auth")
    suspend fun login(
        @Body request: luis_Loginrequest
    ): luis_Loginresponse

    @POST("/containers")
    suspend fun createContainer(
        @Body request: luis_CreateContainerRequest
    ): luis_CreateContainerResponse

    @GET("/containers")
    suspend fun getContainers(): luis_GetContainersResponse

}
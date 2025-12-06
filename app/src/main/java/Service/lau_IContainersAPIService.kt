package Service

import model.lau_ContainersGetResponse
import model.lau_DTOContainers
import model.lau_DTOTechnicians
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

interface lau_IContainersAPIService {@Headers("Content-Type: application/json")
    @POST("/containers")
    suspend fun createContainer(
        @Body data: lau_DTOContainers
    ): lau_ContainersGetResponse

    @Headers("Content-Type: application/json")
    @PUT("/containers/asign")
    suspend fun assignTechnician(
        @Body data: lau_DTOTechnicians
    ): lau_ContainersGetResponse

    @Headers("Content-Type: application/json")
    @PUT("/containers/release")
    suspend fun releaseContainer(
        @Body data: lau_DTOContainers
    ): lau_ContainersGetResponse

}
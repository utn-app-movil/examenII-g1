package Service

import model.lau_AuthGetResponse
import model.lau_DTOAuth
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface lau_IAuthAPIService {
    @Headers("Content-Type: application/json")
    @POST("/users/auth")
    suspend fun authUser(@Body data: lau_DTOAuth): lau_AuthGetResponse
}
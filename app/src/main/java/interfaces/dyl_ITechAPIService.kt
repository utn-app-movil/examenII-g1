package interfaces

import model.dyl_TechResponse
import model.dyl_auth
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface dyl_ITechAPIService {
    @Headers("Content-Type: application/json")
    @POST("/users/auth")
    suspend fun AuthTech(@Body auth: dyl_auth): dyl_TechResponse
}
package interfaces

import model.authresponse_reych_
import retrofit2.http.GET
import retrofit2.http.Path

interface IUSERSAPIService_reych_ {
    @GET("/users")
    suspend fun getAllUsers(): authresponse_reych_

    @GET ("/users/{id}")
    suspend fun getUserById(@Path("id") id: String): authresponse_reych_

}



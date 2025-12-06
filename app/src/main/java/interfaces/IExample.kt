package interfaces

import retrofit2.http.GET
import retrofit2.http.Path


interface IExample {
    @GET("/users")
    suspend fun getAllUsers(): GetResponse_reych

    @GET ("/users/{id}")
    suspend fun getUserById(@Path("id") id: String): GetResponse_reych

}

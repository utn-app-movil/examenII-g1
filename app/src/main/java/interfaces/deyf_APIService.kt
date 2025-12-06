package interfaces

import cr.ac.utn.appmovil.containers.model.deyf_AuthRequest
import cr.ac.utn.appmovil.containers.model.deyf_AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface deyf_APIService {
    @POST("/users/auth")
    fun deyf_login(@Body request: deyf_AuthRequest): Call<deyf_AuthResponse>
}
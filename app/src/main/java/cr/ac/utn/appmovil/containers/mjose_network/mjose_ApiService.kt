package cr.ac.utn.appmovil.containers.mjose_network

import cr.ac.utn.appmovil.containers.mjose_models.mjose_AuthRequest
import cr.ac.utn.appmovil.containers.mjose_models.mjose_AuthResponse
import cr.ac.utn.appmovil.containers.mjose_models.mjose_AssignRequest
import cr.ac.utn.appmovil.containers.mjose_models.mjose_ReleaseRequest
import cr.ac.utn.appmovil.containers.mjose_models.mjose_ContainerCreateRequest
import cr.ac.utn.appmovil.containers.mjose_models.mjose_ContainerResponse
import cr.ac.utn.appmovil.containers.mjose_models.mjose_GenericResponse

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface mjose_ApiService {

    @POST("users/auth")
    fun authenticate(@Body request: mjose_AuthRequest): Call<mjose_AuthResponse>

    @GET("containers")
    fun getContainers(): Call<mjose_ContainerResponse>

    @POST("containers")
    fun createContainer(@Body request: mjose_ContainerCreateRequest): Call<mjose_GenericResponse>

    @PUT("containers/asign")
    fun assignContainer(@Body request: mjose_AssignRequest): Call<mjose_GenericResponse>

    @PUT("containers/release")
    fun releaseContainer(@Body request: mjose_ReleaseRequest): Call<mjose_GenericResponse>
}


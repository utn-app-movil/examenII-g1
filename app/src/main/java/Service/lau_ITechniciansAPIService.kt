package Service

import model.lau_TechniciansGetResponse
import retrofit2.http.GET

interface lau_ITechniciansAPIService {
    @GET("/technicians")
    suspend fun getAllTechnicians(): lau_TechniciansGetResponse
}
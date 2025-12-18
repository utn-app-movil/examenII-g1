package cr.ac.utn.appmovil.containers.kevinv

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import util.util

object kevinv_ApiClient {

    val api: kevinv_IContainersApi by lazy {
        Retrofit.Builder()
            .baseUrl(util.apiURL + "/") // use URL util
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(kevinv_IContainersApi::class.java)
    }
}

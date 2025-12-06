package Service // Asegura que el nombre del paquete sea 'Service'

import interfaces.IyadApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
// 🎯 Importamos la clase 'util' que contiene la URL base
import util.util

object yadApiClient {

    val apiService: IyadApiService by lazy {
        Retrofit.Builder()
            .baseUrl(util.Companion.apiURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IyadApiService::class.java)
    }
}
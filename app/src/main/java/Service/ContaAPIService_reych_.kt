package Service

import interfaces.IUSERSAPIService_reych_
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ContaAPIService_reych_ {

    private const val BASE_URL=""//URL DEL API


    val apiConta: IUSERSAPIService_reych_ by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IUSERSAPIService_reych_::class.java)

    }
}
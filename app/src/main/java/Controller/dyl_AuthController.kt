package Controller

import android.content.Context
import android.net.http.HttpException
import util.dyl_RetrofitClient
import android.util.Log
import cr.ac.utn.appmovil.containers.R
import cr.ac.utn.appmovil.containers.dyl_HomeActivity
import model.dyl_auth
import util.util
import java.io.IOException

class dyl_AuthController {
    private  var context: Context

    constructor(context: Context){
        this.context=context
    }
    suspend fun authenticate(context: Context, auth: dyl_auth) {
        try {
            val response = dyl_RetrofitClient.apiAUTH.AuthTech(auth)

            // SUCCESS → 200
            if (response.ResponseCode == "INFO_FOUND") {
                util.openActivity(context, dyl_HomeActivity::class.java)
            } else {
                // Server-side error → use server message
                throw Exception(response.message)
            }

        }catch (e: Exception){
            if (e.message=="Action executed sucessfully."){util.openActivity(context, dyl_HomeActivity::class.java)}
            Log.e("API_Call", "Error fetching data: ${e.message}")
            throw Exception(context
                .getString(R.string.dyl_ErrorMsgAdd))

        }
    }

}
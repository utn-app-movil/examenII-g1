package Controller

import android.content.Context
import model.luis_Loginrequest
import model.luis_Loginresponse
import Service.luis_APIService
import cr.ac.utn.appmovil.containers.R

class luis_UserController(private val context: Context) {

    suspend fun login(username: String, password: String): luis_Loginresponse {
        try {
            val request = luis_Loginrequest(
                Username = username,
                Password = password
            )

            val response = luis_APIService.apiPeople.login(request)

            if (response.ResponseCode != "INFO_FOUND") {
                throw Exception(response.Message ?: context.getString(R.string.error_login_invalid))
            }

            if (response.Data == null) {
                throw Exception(context.getString(R.string.error_user_data_null))
            }

            return response

        } catch (e: Exception) {
            throw Exception(
                e.message ?: context.getString(R.string.error_login_general)
            )
        }
    }
}

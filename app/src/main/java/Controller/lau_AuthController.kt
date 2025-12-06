package Controller

import Service.lau_APIService
import android.content.Context
import android.util.Log
import model.lau_DTOAuth
import model.lau_AuthGetResponse

class lau_AuthController(private val context: Context) {

    suspend fun authenticate(username: String, password: String): lau_AuthGetResponse? {
        try {
            val dto = lau_DTOAuth(username, password)
            val response = lau_APIService.authService.authUser(dto)

            if (response.responseCode != "INFO_FOUND")
                throw Exception(response.message)

            if (!response.data.any())
                return null

            return response

        } catch (e: Exception) {
            Log.e("API_Auth", "Error: ${e.message}")
            throw Exception("Error authenticating user.")
        }
    }
}

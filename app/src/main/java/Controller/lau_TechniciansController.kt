package Controller

import Service.lau_APIService
import android.content.Context
import android.util.Log
import model.lau_DTOTechnicians

class lau_TechniciansController(private val context: Context) {

    suspend fun getAllTechnicians(): List<lau_DTOTechnicians> {
        val list = mutableListOf<lau_DTOTechnicians>()

        try {
            val response = lau_APIService.techniciansService.getAllTechnicians()

            if (response.responseCode != "INFO_FOUND")
                throw Exception(response.message)

            response.data.forEach { tech ->
                list.add(tech)
            }

            return list

        } catch (e: Exception) {
            Log.e("API_Techs", "Error: ${e.message}")
            throw Exception("Error al obtener técnicos.")
        }
    }
}

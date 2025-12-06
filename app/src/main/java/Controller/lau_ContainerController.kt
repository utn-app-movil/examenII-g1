package Controller

import Service.lau_APIService
import android.content.Context
import android.util.Log
import model.lau_DTOContainers
import model.lau_DTOTechnicians

class lau_ContainerController(private val context: Context) {

    suspend fun createContainer(container: lau_DTOContainers): lau_DTOContainers {
        try {
            val response = lau_APIService.containersService.createContainer(container)

            if (response.responseCode != "INFO_FOUND")
                throw Exception(response.message)

            return response.data[0]

        } catch (e: Exception) {
            Log.e("API_Containers", "Error: ${e.message}")
            throw Exception("Error al crear contenedor.")
        }
    }

    suspend fun assignTechnician(id: String, technicianEmail: String): lau_DTOContainers {
        try {
            val dto = lau_DTOTechnicians(
                ID = id,
                Name = "",
                LastName = "",
                IsActive = true,
                Password = "",
                Email = technicianEmail
            )

            val response = lau_APIService.containersService.assignTechnician(dto)

            if (response.responseCode != "INFO_FOUND")
                throw Exception(response.message)

            return response.data[0]

        } catch (e: Exception) {
            Log.e("API_Containers", "Error: ${e.message}")
            throw Exception("Error al asignar técnico.")
        }
    }

    suspend fun releaseContainer(id: String): lau_DTOContainers {
        try {
            val dto = lau_DTOContainers(id, "", "", "")

            val response = lau_APIService.containersService.releaseContainer(dto)

            if (response.responseCode != "INFO_FOUND")
                throw Exception(response.message)

            return response.data[0]

        } catch (e: Exception) {
            Log.e("API_Containers", "Error: ${e.message}")
            throw Exception("Error al liberar contenedor.")
        }
    }
}

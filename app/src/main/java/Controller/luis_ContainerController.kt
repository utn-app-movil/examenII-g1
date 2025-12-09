package Controller

import android.content.Context
import model.luis_CreateContainerRequest
import model.luis_DTOContainer
import model.luis_GetContainersResponse
import Service.luis_APIService
import cr.ac.utn.appmovil.containers.R

class luis_ContainerController(private val context: Context) {

    suspend fun createContainer(id: String, product: String): luis_DTOContainer {
        try {

            val request = luis_CreateContainerRequest(
                Id = id,
                Product = product
            )

            val response = luis_APIService.apiPeople.createContainer(request)

            if (response.ResponseCode != "SUCESSFUL") {
                throw Exception(response.Message ?: context.getString(R.string.error_create_container))
            }

            if (response.Data == null) {
                throw Exception(context.getString(R.string.error_container_data_null))
            }

            return response.Data

        } catch (e: Exception) {
            throw Exception(
                e.message ?: context.getString(R.string.error_create_container)
            )
        }
    }

    suspend fun getContainers(): List<luis_DTOContainer> {
        try {

            val response: luis_GetContainersResponse =
                luis_APIService.apiPeople.getContainers()

            if (response.ResponseCode != "SUCESSFUL") {
                throw Exception(response.Message ?: context.getString(R.string.error_get_containers))
            }

            if (response.Data == null) {
                throw Exception(context.getString(R.string.error_containers_data_null))
            }

            return response.Data

        } catch (e: Exception) {
            throw Exception(
                e.message ?: context.getString(R.string.error_get_containers)
            )
        }
    }
}

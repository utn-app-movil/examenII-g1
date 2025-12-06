package cr.ac.utn.appmovil.containers.Controller

import Service.yadApiClient
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import model.*


class yadContainerViewModel : ViewModel() {

    private val _containers = MutableLiveData<yadApiResponse<List<yadContainer>>>()
    val containers: LiveData<yadApiResponse<List<yadContainer>>> = _containers

    private val _actionResult = MutableLiveData<yadApiResponse<*>>()
    val actionResult: LiveData<yadApiResponse<*>> = _actionResult

    private val apiService = yadApiClient.apiService

    fun loadContainers() {
        viewModelScope.launch {
            try {

                val response = apiService.getContainers()
                _containers.postValue(response)
            } catch (e: Exception) {
                // ... manejo de errores ...
                _containers.postValue(
                    yadApiResponse(null, "ERROR_NETWORK", "Fallo al cargar la lista: ${e.message}")
                )
            }
        }
    }

    fun createContainer(container: yadContainer) {
        viewModelScope.launch {
            try {
                val response = apiService.createContainer(container)
                _actionResult.postValue(response)

            } catch (e: Exception) {
                _actionResult.postValue(
                    yadApiResponse(null, "ERROR_NETWORK", "Fallo al crear el contenedor: ${e.message}")
                )
            }
        }
    }

    fun assignContainer(containerId: String, technicianEmail: String) {
        viewModelScope.launch {
            try {
                val request = yadAssignRequest(containerId, technicianEmail)
                val response = apiService.assignContainer(request)

                _actionResult.postValue(response)

                if (response.responseCode == "INFO_FOUND" || response.responseCode == "ACTION_SUCCESSFUL") {
                    loadContainers()
                }
            } catch (e: Exception) {
                _actionResult.postValue(
                    yadApiResponse(null, "ERROR_NETWORK", "Fallo al asignar: ${e.message}")
                )
            }
        }
    }

    fun releaseContainer(containerId: String) {
        viewModelScope.launch {
            try {
                val request = yadReleaseRequest(containerId)
                val response = apiService.releaseContainer(request)

                _actionResult.postValue(response)

                if (response.responseCode == "INFO_FOUND" || response.responseCode == "ACTION_SUCCESSFUL") {
                    loadContainers()
                }
            } catch (e: Exception) {
                _actionResult.postValue(
                    yadApiResponse(null, "ERROR_NETWORK", "Fallo al liberar: ${e.message}")
                )
            }
        }
    }
}
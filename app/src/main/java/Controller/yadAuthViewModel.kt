package Controller // Asegura que el paquete sea correcto

import Service.APIService // Importar el objeto APIService que contiene apiPeople
import Service.yadApiClient
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import model.yadApiResponse
import model.yadAuthRequest
import model.yadUser
import util.yadSessionManager
import kotlinx.coroutines.launch
import android.app.Application
import androidx.lifecycle.AndroidViewModel

// ⚠️ NOTA: El import de 'yadApiClient' se ELIMINA porque ya no lo usamos.
// ⚠️ NOTA: El import de 'model' debe ser ajustado a la ruta completa (ej: cr.ac.utn.appmovil.containers.model)

// Los AndroidViewModel son mejores que los ViewModel estándar cuando se requiere Context
class yadAuthViewModel(application: Application) : AndroidViewModel(application) {

    // LiveData para observar el estado del login en la Activity
    val authResult = MutableLiveData<yadApiResponse<yadUser>>()

    // Inicialización del Session Manager usando el Context de la aplicación
    private val sessionManager = yadSessionManager(application.applicationContext)

    // 🎯 AJUSTE CRUCIAL: Usamos la instancia del servicio proporcionada por el profesor.
    // Asumimos que APIService.apiPeople retorna la interfaz IyadApiService.
    private val apiService = yadApiClient.apiService

    /**
     * Autentica al usuario contra el API REST.
     */
    fun authenticateUser(email: String, password: String) {
        // Usa viewModelScope para lanzar la coroutine en el hilo de fondo
        viewModelScope.launch {
            try {
                // 1. Prepara la petición y llama al API
                val request = yadAuthRequest(email, password)

                // 📞 LLAMADA AL SERVICIO: Usa la instancia 'apiService' que es APIService.apiPeople
                val response = apiService.authenticateUser(request)

                // 2. Manejo de Sesión (si el login fue exitoso)
                if (response.responseCode == "INFO_FOUND" && response.data != null) {
                    // **IMPORTANTE**: Guardar el email del técnico logueado
                    sessionManager.saveUserEmail(response.data.email)
                }

                // 3. Postea la respuesta (éxito o error de credenciales) a la Activity
                authResult.postValue(response)

            } catch (e: Exception) {
                // Manejo de errores de conexión/servidor
                authResult.postValue(
                    yadApiResponse(null, "CONNECTION_ERROR", "Error de conexión: ${e.message}")
                )
            }
        }
    }
}
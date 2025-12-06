package cr.ac.utn.appmovil.containers.model

data class kam_ApiResponse<T>(
    val data: T?,
    val responseCode: String,
    val message: String
)
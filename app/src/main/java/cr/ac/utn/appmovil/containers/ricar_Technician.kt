package cr.ac.utn.appmovil.containers

data class ricar_Technician(
    val id: String,
    val name: String,
    val lastName: String,
    val isActive: Boolean,
    val email: String
)

data class ricar_TechniciansResponse(
    val data: List<ricar_Technician>,
    val responseCode: String,
    val message: String
)
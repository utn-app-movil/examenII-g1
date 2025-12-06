package cr.ac.utn.appmovil.containers.model

data class kam_Technician(
    val id: String,
    val name: String,
    val lastName: String,
    val isActive: Boolean,
    val password: String,
    val email: String
)
package cr.ac.utn.appmovil.containers


data class marco_Technician(
    val id: String,
    val name: String,
    val lastName: String,
    val isActive: Boolean,
    val password: String,
    val email: String
)

data class marco_TechniciansResponse(
    val data: List<marco_Technician>?,
    val responseCode: String,
    val message: String
)


data class marco_LoginRequest(
    val username: String,
    val password: String
)

data class marco_LoginUser(
    val user: String,
    val name: String,
    val lastName: String
)

data class marco_LoginResponse(
    val data: marco_LoginUser?,
    val responseCode: String,
    val message: String
)
package cr.ac.utn.appmovil.containers

// ======== MODELOS PARA GET /technicians =========

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

// ======== MODELOS PARA POST /users/auth =========

data class marco_LoginRequest(
    val username: String,
    val password: String
)

data class marco_LoginUser(
    val user: String,
    val name: String,
    val lastName: String
    // en el ejemplo de login no viene email, así que lo dejamos así
)

data class marco_LoginResponse(
    val data: marco_LoginUser?,
    val responseCode: String,
    val message: String
)
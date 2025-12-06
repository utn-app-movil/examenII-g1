package cr.ac.utn.appmovil.containers.kevinv

data class kevinv_AuthRequest(
    val username: String,
    val password: String
)

data class kevinv_AuthUserData(
    val user: String,
    val name: String,
    val lastName: String,
    val email: String?
)

data class kevinv_Technician(
    val id: String,
    val name: String,
    val lastName: String,
    val isActive: Boolean,
    val password: String,
    val email: String
)

data class kevinv_Container(
    val id: String,
    val product: String,
    val technician: String?,
    val date: String?
) {
    val isAssigned: Boolean
        get() = !technician.isNullOrEmpty()
}

data class kevinv_CreateContainerRequest(
    val id: String,
    val product: String
)

data class kevinv_AssignContainerRequest(
    val id: String,
    val technician: String
)

data class kevinv_ReleaseContainerRequest(
    val id: String
)

data class kevinv_ApiResponse<T>(
    val data: T?,
    val responseCode: String,
    val message: String
)
package cr.ac.utn.appmovil.containers.model

data class ApiResponse<T>(
    val data: T?,
    val responseCode: String,
    val message: String
)

data class User(
    val user: String,
    val name: String,
    val lastName: String,
    val email: String
)

data class ContainerRequest(
    val description: String
)

data class Container(
    val id: String,
    val description: String,
    val technicianEmail: String?
)

data class LoginRequest(
    val user: String,
    val password: String
)

data class AssignRequest(
    val id: String,
    val technicianEmail: String
)

data class ReleaseRequest(
    val id: String
)
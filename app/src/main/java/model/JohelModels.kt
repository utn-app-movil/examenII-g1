package model

data class JohelAuthRequest(
    val id: String,
    val password: String
)


data class JohelTechnician(
    val id: String,
    val name: String,
    val lastName: String,
    val isActive: Boolean,
    val password: String,
    val email: String
)

data class JohelBaseResponse<T>(
    val data: T?,
    val responseCode: String,
    val message: String
)

data class JohelContainer(
    val id: Int?,
    val code: String?,          // o name / code según el README
    val description: String?,
    val status: String?,        // ocupado/libre
    val technicianEmail: String?
)

data class JohelContainerRequest(
    val code: String,
    val description: String
)

data class JohelAssignRequest(
    val containerId: Int,
    val technicianEmail: String
)

data class JohelReleaseRequest(
    val containerId: Int
)

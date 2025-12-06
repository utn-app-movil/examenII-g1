package model

data class pau_LoginRequest(
    val email: String,
    val password: String
)

data class pau_CreateContainerRequest(
    val code: String,
    val description: String

)


data class pau_AssignRequest(
    val containerId: Int,
    val technicianEmail: String
)


data class pau_ReleaseRequest(
    val containerId: Int
)
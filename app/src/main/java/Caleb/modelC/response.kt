package Caleb.modelC

// Request body for authentication
data class AuthRequest(
    val username: String,
    val password: String
)

// Response structure
// {
//   "data": { "user": "TEC-03", "name": "Marielos", "lastName": "Barrantes Rojas" },
//   "responseCode": "INFO_FOUND",
//   "message": "Action executed sucessfully."
// }

data class AuthResponse(
    val data: AuthData?,
    val responseCode: String?,
    val message: String?
)

data class AuthData(
    val user: String?,
    val name: String?,
    val lastName: String?
)

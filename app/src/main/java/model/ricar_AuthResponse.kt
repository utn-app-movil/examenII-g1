package model

data class ricar_AuthResponse(
    val data: ricar_User,
    val responseCode: String,
    val message: String
)

data class ricar_User(
    val user: String,
    val name: String,
    val lastName: String
)
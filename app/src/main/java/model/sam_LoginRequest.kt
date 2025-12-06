package model

// Sent JSON to the API for authentication
data class sam_LoginRequest(
    val username: String,
    val password: String
)
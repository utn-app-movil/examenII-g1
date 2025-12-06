package com.utn.examenii.mjose_models

data class mjose_AuthRequest(
    val user: String,
    val password: String
)

data class mjose_User(
    val user: String,
    val name: String,
    val lastName: String,
    val email: String
)

data class mjose_AuthResponse(
    val data: mjose_User?,
    val responseCode: String,
    val message: String
)

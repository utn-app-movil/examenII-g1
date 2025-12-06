package model

data class nid_UserAuthRequest(
    val username: String,
    val password: String
)

// data que viene dentro de "data"
data class nid_UserData(
    val user: String,
    val name: String,
    val lastName: String
)

// Respuesta completa del auth
data class nid_UserAuthResponse(
    val data: nid_UserData?,
    val responseCode: String,
    val message: String
)

package cr.ac.utn.appmovil.containers.mjose_models

data class mjose_AuthResponse(
    val data: mjose_User?,
    val responseCode: String,
    val message: String
)

data class mjose_User(
    val user: String,
    val name: String,
    val lastName: String
)

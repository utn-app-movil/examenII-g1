package cr.ac.utn.appmovil.containers.model

data class kam_User(
    val user: String,
    val name: String,
    val lastName: String,
    val email: String? = null
)
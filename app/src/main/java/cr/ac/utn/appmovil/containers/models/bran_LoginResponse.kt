package cr.ac.utn.appmovil.containers.models

data class BranLoginResponse(
    val data: UserData,
    val responseCode: String,
    val message: String
)

data class UserData(
    val user: String,
    val name: String,
    val lastName: String
)
package cr.ac.utn.appmovil.containers

data class ricar_Container(
    val id: String,
    val product: String,
    val technician: String?,
    val date: String?
)

data class ricar_ContainersResponse(
    val data: List<ricar_Container>,
    val responseCode: String,
    val message: String
)
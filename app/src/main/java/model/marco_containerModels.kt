package cr.ac.utn.appmovil.containers

data class marco_Container(
    val id: String,
    val product: String,
    val technician: String?,
    val date: String?
)

data class marco_ContainerListResponse(
    val data: List<marco_Container>?,
    val responseCode: String,
    val message: String
)

data class marco_ContainerResponse(
    val data: marco_Container?,
    val responseCode: String,
    val message: String
)

data class marco_CreateContainerRequest(
    val id: String,
    val product: String
)

data class marco_AssignContainerRequest(
    val id: String,
    val technician: String
)

data class marco_ReleaseContainerRequest(
    val id: String
)
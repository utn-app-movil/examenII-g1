package cr.ac.utn.appmovil.containers

// Un contenedor
data class marco_Container(
    val id: String,
    val product: String,
    val technician: String?,
    val date: String?
)

// Respuesta cuando devuelve LISTA (GET /containers)
data class marco_ContainerListResponse(
    val data: List<marco_Container>?,
    val responseCode: String,
    val message: String
)

// Respuesta cuando devuelve UN contenedor (create, assign, release)
data class marco_ContainerResponse(
    val data: marco_Container?,
    val responseCode: String,
    val message: String
)

// Body para POST /containers
data class marco_CreateContainerRequest(
    val id: String,
    val product: String
)

// Body para PUT /containers/asign
data class marco_AssignContainerRequest(
    val id: String,
    val technician: String
)

// Body para PUT /containers/release
data class marco_ReleaseContainerRequest(
    val id: String
)
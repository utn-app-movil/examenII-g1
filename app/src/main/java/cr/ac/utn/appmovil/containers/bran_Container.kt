package cr.ac.utn.appmovil.containers

data class ContainerResponse(val data: List<Container>)

data class Container(
    val id: String,
    val product: String,
    val technician: String?,
    val date: String?
)

data class AssignRequest(val id: String, val technician: String)

data class ReleaseRequest(val id: String)

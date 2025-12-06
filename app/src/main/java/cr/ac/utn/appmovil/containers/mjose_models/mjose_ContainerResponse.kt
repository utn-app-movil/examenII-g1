package cr.ac.utn.appmovil.containers.mjose_models

data class mjose_ContainerResponse(
    val data: List<mjose_Container>?,
    val responseCode: String,
    val message: String
)


package Caleb.modelC

// Request para asignar técnico al contenedor
// {
//   "id": "CR1244K5UJ17367",
//   "technician": "cugalde@gmail.com"
// }
data class ContainerAssignRequest(
    val id: String,
    val technician: String
)

// Request para liberar contenedor
// { "id": "CR1244K5UJ17367" }
data class ContainerReleaseRequest(
    val id: String
)

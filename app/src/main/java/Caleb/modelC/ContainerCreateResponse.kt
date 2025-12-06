package Caleb.modelC

// Request body for creating a container
data class ContainerCreateRequest(
    val id: String,
    val product: String
)

// Response structure example:
// {
//   "data": {"id":"CR5T6A3B85Q5JK7","product":"Metal","technician":"","date":""},
//   "responseCode":"SUCESSFUL",
//   "message":"Action executed sucessfully."
// }

data class ContainerCreateResponse(
    val data: ContainerData?,
    val responseCode: String?,
    val message: String?
)

data class ContainerData(
    val id: String?,
    val product: String?,
    val technician: String?,
    val date: String?
)


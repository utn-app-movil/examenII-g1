package model

data class lau_ContainersGetResponse(
    val data: List<lau_DTOContainers>,
    val message: String,
    val responseCode: String
)

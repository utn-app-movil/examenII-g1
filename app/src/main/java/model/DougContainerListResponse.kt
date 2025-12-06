package model

data class DougContainerListResponse(
    val data: List<DougContainer>?,
    val responseCode: String,
    val message: String
)
package model

data class lau_AuthGetResponse(
    val data: List<lau_DTOAuth>,
    val message: String,
    val responseCode: String
)

package model

data class DougAuthResponse(
    val data: DougUser?,
    val responseCode: String,
    val message: String
)
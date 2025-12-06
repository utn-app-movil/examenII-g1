package model

data class lau_TechniciansGetResponse(
    val data: List<lau_DTOTechnicians>,
    val message: String,
    val responseCode: String
)

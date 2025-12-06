package model

data class nid_Technician(
    val id: String,
    val name: String,
    val lastName: String,
    val isActive: Boolean,
    val password: String,
    val email: String
)

data class nid_TechnicianResponse(
    val data: List<nid_Technician>,
    val responseCode: String,
    val message: String
)

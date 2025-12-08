package model

// Structure model of a technician
data class sam_Technician(
    val id: String,
    val name: String,
    val lastName: String,
    val isActive: Boolean,
    val password: String,
    val email: String
)
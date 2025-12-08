package model

// Generic Answers of the API
data class sam_ApiResponse<T>(
    val data: T?,
    val responseCode: String,
    val message: String
)
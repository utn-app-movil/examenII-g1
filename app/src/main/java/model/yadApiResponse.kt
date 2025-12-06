package model

// Archivo: model/yadApiResponse.kt

data class yadApiResponse<T>(
    val data: T?,
    val responseCode: String,
    val message: String
)

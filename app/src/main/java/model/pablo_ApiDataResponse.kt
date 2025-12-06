package model

import android.R


data class pablo_ApiData(
    val user: String,
    val name: String,
    val lastName: String
)
data class pablo_ApiDataResponse(
    val data: pablo_ApiData?,
    val responseCode: String,
    val message: String
)

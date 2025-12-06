package model

import com.google.gson.annotations.SerializedName

data class lau_DTOContainers(
    @SerializedName("id") val ID: String,
    @SerializedName("product") val Product: String = "",
    @SerializedName("technician") val Technician: String = "",
    @SerializedName("date") val Date: String = ""
)

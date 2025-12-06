package model

import com.google.gson.annotations.SerializedName

data class lau_DTOTechnicians(
    @SerializedName("id") val ID: String,
    @SerializedName("name") val Name: String = "",
    @SerializedName("lastName") val LastName: String = "",
    @SerializedName("isActive") val IsActive: Boolean = true,
    @SerializedName("password") val Password: String = "",
    @SerializedName("email") val Email: String = ""
)

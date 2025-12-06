package model

import com.google.gson.annotations.SerializedName

data class lau_DTOAuth(
    @SerializedName("username") val Username: String = "",
    @SerializedName("password") val Password: String = ""
)

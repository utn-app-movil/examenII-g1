package model

import com.google.gson.annotations.SerializedName

data class luis_DTOContainer(

    @SerializedName("id") val Id: String = "",
    @SerializedName("product") val Product: String = "",
    @SerializedName("technician") val Technician: String = "",
    @SerializedName("date") val Date: String = ""

)


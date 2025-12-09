package model

import com.google.gson.annotations.SerializedName

data class luis_DTOlogin(

    @SerializedName("user") val User: String = "",
    @SerializedName("name") val Name: String = "",
    @SerializedName("lastName") val LastName: String = ""

)

package model

import com.google.gson.annotations.SerializedName

data class luis_CreateContainerRequest(

    @SerializedName("id") val Id: String = "",
    @SerializedName("product") val Product: String = ""

)


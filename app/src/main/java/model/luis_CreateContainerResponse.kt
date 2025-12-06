package model

import com.google.gson.annotations.SerializedName

data class luis_CreateContainerResponse(

    @SerializedName("data") val Data: luis_DTOContainer? = null,
    @SerializedName("responseCode") val ResponseCode: String = "",
    @SerializedName("message") val Message: String = ""

)


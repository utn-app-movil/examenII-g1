package model

import com.google.gson.annotations.SerializedName

data class luis_GetContainersResponse(

    @SerializedName("data") val Data: List<luis_DTOContainer>? = null,
    @SerializedName("responseCode") val ResponseCode: String = "",
    @SerializedName("message") val Message: String = ""

)


package model

import com.google.gson.annotations.SerializedName

data class luis_Loginresponse(

    @SerializedName("data") val Data: luis_DTOlogin? = null,
    @SerializedName("responseCode") val ResponseCode: String = "",
    @SerializedName("message") val Message: String = ""

)

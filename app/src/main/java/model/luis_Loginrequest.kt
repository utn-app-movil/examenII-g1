package model

import com.google.gson.annotations.SerializedName

data class luis_Loginrequest(

    @SerializedName("username") val Username: String = "",
    @SerializedName("password") val Password: String = ""

)

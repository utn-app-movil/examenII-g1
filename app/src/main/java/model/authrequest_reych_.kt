package model

import com.google.gson.annotations.SerializedName

data class authrequest_reych_(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("lastname") val lastname: String,
    @SerializedName("isActive") val active: Boolean,
    @SerializedName("password") val password: String,
    @SerializedName("email") val email: String,
    )


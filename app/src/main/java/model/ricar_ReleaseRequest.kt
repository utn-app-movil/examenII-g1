package model

import com.google.gson.annotations.SerializedName

data class ricar_ReleaseRequest(
    @SerializedName("container_id") val containerId: String
)
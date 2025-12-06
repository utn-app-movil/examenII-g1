package model

import com.google.gson.annotations.SerializedName

data class ricar_AssignRequest(
    @SerializedName("container_id") val containerId: String,
    @SerializedName("technician_email") val technicianEmail: String
)
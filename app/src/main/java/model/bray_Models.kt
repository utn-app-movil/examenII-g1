package cr.ac.utn.appmovil.containers

import com.google.gson.annotations.SerializedName

data class bray_LoginRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)

data class bray_LoginResponse(
    @SerializedName("data") val data: bray_UserData?,
    @SerializedName("responseCode") val responseCode: String,
    @SerializedName("message") val message: String
)

data class bray_UserData(
    @SerializedName("user") val user: String,
    @SerializedName("name") val name: String,
    @SerializedName("lastName") val lastName: String
)

data class bray_Technician(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("user") val user: String?
)

data class bray_ContainerRequest(
    @SerializedName("id") val id: String,
    @SerializedName("product") val product: String
)

data class bray_ContainerResponse(
    @SerializedName("data") val data: bray_ContainerData?,
    @SerializedName("responseCode") val responseCode: String,
    @SerializedName("message") val message: String
)

data class bray_ContainerData(
    @SerializedName("id") val id: String,
    @SerializedName("product") val product: String,
    @SerializedName("technician") val technician: String?,
    @SerializedName("date") val date: String?
)

data class bray_AssignContainerRequest(
    @SerializedName("id") val id: String,
    @SerializedName("technician") val technician: String
)

data class bray_ReleaseContainerRequest(
    @SerializedName("id") val id: String
)

data class bray_ContainerListResponse(
    @SerializedName("data") val data: List<bray_ContainerData>?,
    @SerializedName("responseCode") val responseCode: String,
    @SerializedName("message") val message: String
)

data class bray_GenericResponse(
    @SerializedName("responseCode") val responseCode: String,
    @SerializedName("message") val message: String
)

package model

data class pau_UserResponse(
    val data: pau_User?
) : pau_BaseResponse(pau_responseCode = "", pau_message = "")

data class pau_User(
    val pau_userCode: String,
    val pau_name: String,
    val pau_lastName: String,
    val pau_email: String
)

data class pau_Container(
    val pau_id: Int,
    val pau_code: String,
    val pau_description: String,
    val pau_technicianEmail: String?
) {

    val pau_isAssigned: Boolean
        get() = pau_technicianEmail != null
}

data class pau_ContainerListResponse(
    val data: List<pau_Container>?
) : pau_BaseResponse(pau_responseCode = "", pau_message = "")
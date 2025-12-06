package Service

import model.*

object pau_Repository {
    private val api = pau_APIService.pau_IAPIService

    suspend fun pau_login(request: pau_LoginRequest) = api.pau_authenticateUser(request)
    suspend fun pau_getContainers() = api.pau_getContainers()
    suspend fun pau_createContainer(request: pau_CreateContainerRequest) = api.pau_createContainer(request)
    suspend fun pau_assignContainer(request: pau_AssignRequest) = api.pau_assignContainer(request)
    suspend fun pau_releaseContainer(request: pau_ReleaseRequest) = api.pau_releaseContainer(request)
}
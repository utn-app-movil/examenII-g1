package cr.ac.utn.appmovil.containers.kevinv

object kevinv_SessionManager {

    var loggedUserId: String? = null
    var loggedUserName: String? = null
    var loggedUserLastName: String? = null
    var loggedUserEmail: String? = null

    fun clear() {
        loggedUserId = null
        loggedUserName = null
        loggedUserLastName = null
        loggedUserEmail = null
    }
}

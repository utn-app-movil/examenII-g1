package cr.ac.utn.appmovil.containers.model

data class kam_Container(
    val id: String,
    val product: String,
    val technician: String,
    val date: String?
) {
    fun isOccupied(): Boolean = technician.isNotEmpty()
    fun isFree(): Boolean = technician.isEmpty()
}
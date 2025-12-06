package model

data class yadContainer(
    val containerId: String,        // Identificador único
    val name: String,
    val location: String,


    val assignedTo: String?
)
{

    fun isOccupied(): Boolean {

        return !assignedTo.isNullOrEmpty()
    }
}

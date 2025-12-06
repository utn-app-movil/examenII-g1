package adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.appmovil.containers.R
import model.yadContainer

class yadContainerAdapter(

    private var containers: List<yadContainer>,
    private val onActionButtonClicked: (containerId: String, actionType: String) -> Unit
) : RecyclerView.Adapter<yadContainerAdapter.yadContainerViewHolder>() {


    companion object {
        const val ACTION_ASSIGN = "ASSIGN"
        const val ACTION_RELEASE = "RELEASE"
    }

    inner class yadContainerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.yad_container_name)
        val statusTextView: TextView = itemView.findViewById(R.id.yad_container_status)
        val actionButton: Button = itemView.findViewById(R.id.yad_container_action_button)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): yadContainerViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_yad_container, parent, false)
        return yadContainerViewHolder(view)
    }


    override fun onBindViewHolder(holder: yadContainerViewHolder, position: Int) {
        val container = containers[position]

        holder.nameTextView.text = container.name


        if (container.isOccupied()) {
            holder.statusTextView.text = "ESTADO: OCUPADO por ${container.assignedTo}"
            holder.actionButton.text = "LIBERAR"
            // Cambiar color de fondo del item si lo deseas
            holder.actionButton.setOnClickListener {
                onActionButtonClicked(container.containerId, ACTION_RELEASE)
            }
        } else {
            holder.statusTextView.text = "ESTADO: LIBERADO"
            holder.actionButton.text = "ASIGNARME"
            // Cambiar color de fondo del item si lo deseas
            holder.actionButton.setOnClickListener {
                onActionButtonClicked(container.containerId, ACTION_ASSIGN)
            }
        }
    }

    override fun getItemCount(): Int = containers.size

    fun updateList(newContainers: List<yadContainer>) {
        this.containers = newContainers
        notifyDataSetChanged()
    }
}
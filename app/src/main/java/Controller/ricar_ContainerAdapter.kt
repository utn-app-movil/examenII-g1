package Controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.appmovil.containers.R
import cr.ac.utn.appmovil.containers.ricar_Container

class ricar_ContainerAdapter(
    private var containers: List<ricar_Container>,
    private val onAssign: (ricar_Container) -> Unit,
    private val onRelease: (ricar_Container) -> Unit
) : RecyclerView.Adapter<ricar_ContainerAdapter.ContainerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContainerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ricar_item_container, parent, false)
        return ContainerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContainerViewHolder, position: Int) {
        holder.bind(containers[position])
    }

    override fun getItemCount(): Int = containers.size

    fun updateData(newContainers: List<ricar_Container>) {
        containers = newContainers
        notifyDataSetChanged()
    }

    inner class ContainerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.containerName)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.containerDescription)
        private val technicianEmailTextView: TextView = itemView.findViewById(R.id.technicianEmail)
        private val assignButton: Button = itemView.findViewById(R.id.assignButton)
        private val releaseButton: Button = itemView.findViewById(R.id.releaseButton)

        fun bind(container: ricar_Container) {
            nameTextView.text = container.product
            descriptionTextView.text = "ID: ${container.id}"


            if (!container.technician.isNullOrEmpty()) {
                technicianEmailTextView.text = "Asignado a: ${container.technician}"
                technicianEmailTextView.visibility = View.VISIBLE
                assignButton.visibility = View.GONE
                releaseButton.visibility = View.VISIBLE
            } else {
                technicianEmailTextView.visibility = View.GONE
                assignButton.visibility = View.VISIBLE
                releaseButton.visibility = View.GONE
            }

            assignButton.setOnClickListener { onAssign(container) }
            releaseButton.setOnClickListener { onRelease(container) }
        }
    }
}
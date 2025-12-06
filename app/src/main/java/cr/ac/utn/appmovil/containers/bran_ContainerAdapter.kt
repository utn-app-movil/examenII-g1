package cr.ac.utn.appmovil.containers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class bran_ContainerAdapter(
    private val containers: MutableList<Container>,
    private val onAssignClick: (Container) -> Unit,
    private val onReleaseClick: (Container) -> Unit
) : RecyclerView.Adapter<bran_ContainerAdapter.ContainerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContainerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bran_item_container, parent, false)
        return ContainerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContainerViewHolder, position: Int) {
        val container = containers[position]
        holder.bind(container)
    }

    override fun getItemCount() = containers.size

    fun updateData(newContainers: List<Container>) {
        containers.clear()
        containers.addAll(newContainers)
        notifyDataSetChanged()
    }

    inner class ContainerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val containerId: TextView = itemView.findViewById(R.id.container_id)
        private val productName: TextView = itemView.findViewById(R.id.product_name)
        private val technicianName: TextView = itemView.findViewById(R.id.technician_name)
        private val assignButton: Button = itemView.findViewById(R.id.assign_button)
        private val releaseButton: Button = itemView.findViewById(R.id.release_button)

        fun bind(container: Container) {
            containerId.text = container.id
            productName.text = "Product: ${container.product}"
            technicianName.text = "Technician: ${container.technician ?: "Not assigned"}"

            assignButton.setOnClickListener { onAssignClick(container) }
            releaseButton.setOnClickListener { onReleaseClick(container) }
        }
    }
}

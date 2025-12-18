package cr.ac.utn.appmovil.containers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class bray_ContainerAdapter(
    private var containers: List<bray_ContainerData>,
    private val onAssignClick: (bray_ContainerData) -> Unit,
    private val onReleaseClick: (bray_ContainerData) -> Unit
) : RecyclerView.Adapter<bray_ContainerAdapter.ContainerViewHolder>() {

    inner class ContainerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvContainerId: TextView = itemView.findViewById(R.id.tvContainerId)
        val tvProduct: TextView = itemView.findViewById(R.id.tvProduct)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val tvTechnician: TextView = itemView.findViewById(R.id.tvTechnician)
        val btnAction: Button = itemView.findViewById(R.id.btnAction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContainerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bray_item_container, parent, false)
        return ContainerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContainerViewHolder, position: Int) {
        val container = containers[position]

        holder.tvContainerId.text = "ID: ${container.id}"
        holder.tvProduct.text = "Producto: ${container.product}"
        
        val isOccupied = !container.technician.isNullOrEmpty()
        
        if (isOccupied) {
            holder.tvStatus.text = "Estado: Ocupado"
            holder.tvTechnician.visibility = View.VISIBLE
            holder.tvTechnician.text = "Técnico: ${container.technician}"
            holder.btnAction.text = "Liberar"
            holder.btnAction.setOnClickListener { onReleaseClick(container) }
        } else {
            holder.tvStatus.text = "Estado: Liberado"
            holder.tvTechnician.visibility = View.GONE
            holder.btnAction.text = "Asignar a mi"
            holder.btnAction.setOnClickListener { onAssignClick(container) }
        }
    }

    override fun getItemCount(): Int = containers.size

    fun updateData(newContainers: List<bray_ContainerData>) {
        containers = newContainers
        notifyDataSetChanged()
    }
}
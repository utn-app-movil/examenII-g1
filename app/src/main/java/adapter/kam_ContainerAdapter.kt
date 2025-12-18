package adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.appmovil.containers.R
import cr.ac.utn.appmovil.containers.model.kam_Container

class kam_ContainerAdapter(
    private var containers: List<kam_Container>,
    private val onAssign: (kam_Container) -> Unit,
    private val onRelease: (kam_Container) -> Unit
) : RecyclerView.Adapter<kam_ContainerAdapter.ContainerViewHolder>() {

    class ContainerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvId: TextView = view.findViewById(R.id.kam_tv_container_id)
        val tvProduct: TextView = view.findViewById(R.id.kam_tv_product)
        val tvTechnician: TextView = view.findViewById(R.id.kam_tv_technician)
        val tvDate: TextView = view.findViewById(R.id.kam_tv_date)
        val tvStatus: TextView = view.findViewById(R.id.kam_tv_status)
        val btnAction: Button = view.findViewById(R.id.kam_btn_action)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContainerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kam_container, parent, false)
        return ContainerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContainerViewHolder, position: Int) {
        val container = containers[position]

        holder.tvId.text = "ID: ${container.id}"
        holder.tvProduct.text = "Product: ${container.product}"

        if (container.isOccupied()) {
            // Container is occupied
            holder.tvStatus.text = "OCCUPIED"
            holder.tvStatus.setTextColor(Color.RED)
            holder.tvTechnician.text = "Technician: ${container.technician}"
            holder.tvDate.text = "Date: ${container.date ?: "N/A"}"
            holder.btnAction.text = "Release"
            holder.btnAction.setBackgroundColor(Color.parseColor("#FF6B6B"))
            holder.btnAction.setOnClickListener { onRelease(container) }
        } else {
            // Container is free
            holder.tvStatus.text = "FREE"
            holder.tvStatus.setTextColor(Color.GREEN)
            holder.tvTechnician.text = "Technician: Not assigned"
            holder.tvDate.text = "Date: N/A"
            holder.btnAction.text = "Assign"
            holder.btnAction.setBackgroundColor(Color.parseColor("#4CAF50"))
            holder.btnAction.setOnClickListener { onAssign(container) }
        }
    }

    override fun getItemCount() = containers.size

    fun updateContainers(newContainers: List<kam_Container>) {
        containers = newContainers
        notifyDataSetChanged()
    }
}
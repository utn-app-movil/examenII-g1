package cr.ac.utn.appmovil.containers.kevinv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.appmovil.containers.R

class kevinv_ContainerAdapter(
    private var items: MutableList<kevinv_Container>,
    private val onAssignClick: (kevinv_Container) -> Unit,
    private val onReleaseClick: (kevinv_Container) -> Unit
) : RecyclerView.Adapter<kevinv_ContainerAdapter.kevinv_ContainerViewHolder>() {


    class kevinv_ContainerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lblId: TextView = itemView.findViewById(R.id.kevinv_lblContainerId)
        val lblProduct: TextView = itemView.findViewById(R.id.kevinv_lblProduct)
        val lblStatus: TextView = itemView.findViewById(R.id.kevinv_lblStatus)
        val btnAction: Button = itemView.findViewById(R.id.kevinv_btnAction)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): kevinv_ContainerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kevinv_container, parent, false)
        return kevinv_ContainerViewHolder(view)
    }

    override fun onBindViewHolder(holder: kevinv_ContainerViewHolder, position: Int) {
        val container = items[position]
        holder.lblId.text = container.id
        holder.lblProduct.text = container.product

        if (container.isAssigned) {
            holder.lblStatus.text = "Assigned to: ${container.technician}"
            holder.btnAction.text = "Release"
            holder.btnAction.setOnClickListener { onReleaseClick(container) }
        } else {
            holder.lblStatus.text = "Free"
            holder.btnAction.text = "Assign"
            holder.btnAction.setOnClickListener { onAssignClick(container) }
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<kevinv_Container>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}

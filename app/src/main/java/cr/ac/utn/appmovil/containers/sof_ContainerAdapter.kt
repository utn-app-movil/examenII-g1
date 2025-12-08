package cr.ac.utn.appmovil.containers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.appmovil.containers.model.Container

class sof_ContainerAdapter(
    private val onAssignClick: (Container) -> Unit,
    private val onReleaseClick: (Container) -> Unit
) : RecyclerView.Adapter<sof_ContainerAdapter.ContainerViewHolder>() {

    private var containers = listOf<Container>()

    fun setData(newContainers: List<Container>) {
        containers = newContainers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContainerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sof_itemcontainer, parent, false)
        return ContainerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContainerViewHolder, position: Int) {
        holder.bind(containers[position])
    }

    override fun getItemCount() = containers.size

    inner class ContainerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtId: TextView = itemView.findViewById(R.id.sof_containerID)
        private val txtDesc: TextView = itemView.findViewById(R.id.sof_container_Desc)
        private val txtStatus: TextView = itemView.findViewById(R.id.sof_containerStatus)
        private val txtTech: TextView = itemView.findViewById(R.id.sof_containerTech)
        private val btnAction: Button = itemView.findViewById(R.id.sof_btnAction)

        fun bind(container: Container) {
            txtId.text = "ID: ${container.id}"
            txtDesc.text = "Description: ${container.description}"


            if (!container.technicianEmail.isNullOrEmpty()) {
                txtStatus.text = "Status: ASSIGNED"
                txtTech.text = "Technician: ${container.technicianEmail}"
                txtTech.visibility = View.VISIBLE
                btnAction.text = "Release"
                btnAction.setOnClickListener { onReleaseClick(container) }
            } else {
                txtStatus.text = "Status: AVAILABLE"
                txtTech.visibility = View.GONE
                btnAction.text = "Assign to me"
                btnAction.setOnClickListener { onAssignClick(container) }
            }
        }
    }
}
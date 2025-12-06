package adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.appmovil.containers.R
import model.DougContainer

class DougContainerAdapter(
    private var containers: List<DougContainer>,
    private val onAssignClick: (DougContainer) -> Unit,
    private val onReleaseClick: (DougContainer) -> Unit,
    private val currentUserEmail: String
) : RecyclerView.Adapter<DougContainerAdapter.DougContainerViewHolder>() {

    inner class DougContainerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dougCardView: CardView = itemView.findViewById(R.id.doug_card_view)
        val dougNumberTextView: TextView = itemView.findViewById(R.id.doug_container_number)
        val dougSizeTextView: TextView = itemView.findViewById(R.id.doug_container_size)
        val dougTypeTextView: TextView = itemView.findViewById(R.id.doug_container_type)
        val dougStatusTextView: TextView = itemView.findViewById(R.id.doug_container_status)
        val dougTechnicianTextView: TextView = itemView.findViewById(R.id.doug_container_technician)
        val dougActionButton: Button = itemView.findViewById(R.id.doug_action_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DougContainerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.doug_container_item, parent, false)
        return DougContainerViewHolder(view)
    }

    override fun onBindViewHolder(holder: DougContainerViewHolder, position: Int) {
        val container = containers[position]

        holder.dougNumberTextView.text = "ID: ${container.id}"
        holder.dougSizeTextView.text = "Product: ${container.product}"
        holder.dougTypeTextView.text = "Date: ${container.date ?: "Not assigned"}"

        // Determine status based on technician
        val isFree = container.technician.isNullOrEmpty()
        holder.dougStatusTextView.text = "Status: ${if (isFree) "Free" else "Occupied"}"

        if (!container.technician.isNullOrEmpty()) {
            holder.dougTechnicianTextView.visibility = View.VISIBLE
            holder.dougTechnicianTextView.text = "Technician: ${container.technician}"
        } else {
            holder.dougTechnicianTextView.visibility = View.GONE
        }

        if (isFree) {
            holder.dougActionButton.text = "Assign"
            holder.dougActionButton.setBackgroundColor(
                holder.itemView.context.getColor(R.color.teal_700)
            )
            holder.dougActionButton.setOnClickListener {
                onAssignClick(container)
            }
            holder.dougCardView.setCardBackgroundColor(
                holder.itemView.context.getColor(R.color.teal_200)
            )
        } else {
            holder.dougActionButton.text = "Release"
            holder.dougActionButton.setBackgroundColor(
                holder.itemView.context.getColor(R.color.purple_700)
            )
            holder.dougActionButton.setOnClickListener {
                onReleaseClick(container)
            }
            holder.dougCardView.setCardBackgroundColor(
                holder.itemView.context.getColor(R.color.purple_200)
            )
        }
    }

    override fun getItemCount(): Int = containers.size

    fun updateContainers(newContainers: List<DougContainer>) {
        containers = newContainers
        notifyDataSetChanged()
    }
}
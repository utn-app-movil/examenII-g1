package cr.ac.utn.appmovil.containers.mjose_activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.appmovil.containers.R
import cr.ac.utn.appmovil.containers.mjose_models.mjose_Container


class mjose_ContainerAdapter(
    private var items: List<mjose_Container>,
    private val onAssign: (mjose_Container) -> Unit,
    private val onRelease: (mjose_Container) -> Unit
) : RecyclerView.Adapter<mjose_ContainerAdapter.mjose_ContainerViewHolder>() {

    fun updateData(newItems: List<mjose_Container>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class mjose_ContainerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtCode: TextView = view.findViewById(R.id.mjose_txtCode)
        val txtStatus: TextView = view.findViewById(R.id.mjose_txtStatus)
        val btnAssign: Button = view.findViewById(R.id.mjose_btnAssign)
        val btnRelease: Button = view.findViewById(R.id.mjose_btnRelease)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mjose_ContainerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mjose_container, parent, false)
        return mjose_ContainerViewHolder(view)
    }

    override fun onBindViewHolder(holder: mjose_ContainerViewHolder, position: Int) {
        val container = items[position]

        holder.txtCode.text = container.code

        if (container.available) {
            holder.txtStatus.text = "Available"
            holder.btnAssign.visibility = View.VISIBLE
            holder.btnRelease.visibility = View.GONE
        } else {
            holder.txtStatus.text = "Assigned to ${container.technician ?: ""}"
            holder.btnAssign.visibility = View.GONE
            holder.btnRelease.visibility = View.VISIBLE
        }

        holder.btnAssign.setOnClickListener { onAssign(container) }
        holder.btnRelease.setOnClickListener { onRelease(container) }
    }

    override fun getItemCount(): Int = items.size
}

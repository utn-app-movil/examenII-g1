package adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.appmovil.containers.R
import model.sam_Container

class sam_ContainerAdapter(
    private val items: MutableList<sam_Container>,
    private val onAssignClick: (sam_Container) -> Unit,
    private val onReleaseClick: (sam_Container) -> Unit
) : RecyclerView.Adapter<sam_ContainerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtId: TextView = view.findViewById(R.id.txtContainerId)
        val txtProduct: TextView = view.findViewById(R.id.txtProduct)
        val txtTechnician: TextView = view.findViewById(R.id.txtTechnician)
        val txtDate: TextView = view.findViewById(R.id.txtDate)
        val btnAssign: Button = view.findViewById(R.id.btnAssign)
        val btnRelease: Button = view.findViewById(R.id.btnRelease)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sam_item_container, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val container = items[position]

        holder.txtId.text = container.id
        holder.txtProduct.text = "Producto: ${container.product}"
        holder.txtTechnician.text = "Técnico: ${container.technician ?: "-"}"
        holder.txtDate.text = "Fecha: ${container.date ?: "-"}"

        val isFree = container.technician.isNullOrEmpty()
        holder.btnAssign.isEnabled = isFree
        holder.btnRelease.isEnabled = !isFree

        holder.btnAssign.setOnClickListener { onAssignClick(container) }
        holder.btnRelease.setOnClickListener { onReleaseClick(container) }
    }

    fun replaceAll(newItems: List<sam_Container>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}

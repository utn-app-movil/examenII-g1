package cr.ac.utn.appmovil.containers.pau_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.appmovil.containers.R
import model.pau_Container

class pau_ContainerAdapter(
    private val pau_onAssign: (Int) -> Unit,
    private val pau_onRelease: (Int) -> Unit,
    private val pau_loggedEmail: String,
    val pau_itemList: MutableList<pau_Container>
) : RecyclerView.Adapter<pau_ContainerAdapter.pau_ContainerViewHolder>() {

    inner class pau_ContainerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pau_code_text: TextView = itemView.findViewById(R.id.pau_code_text)
        val pau_status_text: TextView = itemView.findViewById(R.id.pau_status_text)
        val pau_assign_button: Button = itemView.findViewById(R.id.pau_assign_button)
        val pau_release_button: Button = itemView.findViewById(R.id.pau_release_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): pau_ContainerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_pau_containeritem, parent, false)
        return pau_ContainerViewHolder(view)
    }

    override fun onBindViewHolder(holder: pau_ContainerViewHolder, position: Int) {
        val container = pau_itemList[position]
        val context = holder.itemView.context

        holder.pau_code_text.text = "CONTAINER #${container.pau_code}"

        val assignedEmail = container.pau_technicianEmail
        val estaOcupado = !assignedEmail.isNullOrBlank()
        val esMio = assignedEmail == pau_loggedEmail

        holder.pau_assign_button.visibility = View.GONE
        holder.pau_release_button.visibility = View.GONE

        if (estaOcupado) {
            holder.pau_status_text.text = "Asignado a: $assignedEmail"
            holder.pau_status_text.setTextColor(context.getColor(android.R.color.holo_red_dark))
            if (esMio) {
                holder.pau_release_button.visibility = View.VISIBLE
                holder.pau_release_button.setOnClickListener {
                    pau_onRelease(container.pau_id)
                }
            }
        } else {
            holder.pau_status_text.text = "LIBRE"
            holder.pau_status_text.setTextColor(context.getColor(android.R.color.holo_green_dark))
            holder.pau_assign_button.visibility = View.VISIBLE
            holder.pau_assign_button.setOnClickListener {
                pau_onAssign(container.pau_id)
            }
        }
    }

    override fun getItemCount() = pau_itemList.size
}
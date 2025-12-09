package adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.appmovil.containers.R
import interfaces.luis_IOnContainerClickListener
import model.luis_DTOContainer

class luis_ContainerListAdapter(
    private var itemList: List<luis_DTOContainer>,
    val itemClickListener: luis_IOnContainerClickListener
) : RecyclerView.Adapter<luis_ContainerListAdapter.ContainerViewHolder>() {

    class ContainerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtId: TextView = view.findViewById(R.id.txtContainerId_recycler)
        var txtProduct: TextView = view.findViewById(R.id.txtProduct_recycler)
        var txtTechnician: TextView = view.findViewById(R.id.txtTechnician_recycler)
        var txtDate: TextView = view.findViewById(R.id.txtDate_recycler)

        fun bind(
            item: luis_DTOContainer,
            clickListener: luis_IOnContainerClickListener
        ) {
            txtId.text = item.Id
            txtProduct.text = item.Product
            txtTechnician.text = item.Technician
            txtDate.text = item.Date

            itemView.setOnClickListener {
                clickListener.onContainerClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContainerViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_container, parent, false)
        return ContainerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContainerViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item, itemClickListener)
    }

    override fun getItemCount(): Int = itemList.size

    fun updateList(newList: List<luis_DTOContainer>) {
        itemList = newList
        notifyDataSetChanged()
    }
}

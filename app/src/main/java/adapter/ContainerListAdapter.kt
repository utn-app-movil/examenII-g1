import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.appmovil.containers.R
import interfaces.lau_OnItemClickListener
import model.lau_DTOContainers

class ContainerListAdapter(
    private var itemList: List<lau_DTOContainers>,
    private val itemClickListener: lau_OnItemClickListener.OnItemClickListener
) : RecyclerView.Adapter<ContainerListAdapter.ContainerViewHolder>() {

    class ContainerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtContainerId: TextView = view.findViewById(R.id.lau_container_id_item)
        val txtProduct: TextView = view.findViewById(R.id.lau_product_item)
        val btnAssign: Button = view.findViewById(R.id.lau_btn_assign)
        val btnRelease: Button = view.findViewById(R.id.lau_btn_release)

        fun bind(item: lau_DTOContainers, clickListener: lau_OnItemClickListener.OnItemClickListener) {
            txtContainerId.text = "Container ID: ${item.ID}"
            txtProduct.text = "Product: ${item.Product}"

            itemView.setOnClickListener { clickListener.onItemClicked(item) }
            btnAssign.setOnClickListener { clickListener.onAssignClicked(item) }
            btnRelease.setOnClickListener { clickListener.onReleaseClicked(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContainerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_lau_list_containers, parent, false)
        return ContainerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContainerViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item, itemClickListener)
    }

    override fun getItemCount(): Int = itemList.size

    fun updateItems(newList: List<lau_DTOContainers>) {
        itemList = newList
        notifyDataSetChanged()
    }
}

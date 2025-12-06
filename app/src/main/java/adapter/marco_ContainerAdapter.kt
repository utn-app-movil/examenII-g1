package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import cr.ac.utn.appmovil.containers.R
import cr.ac.utn.appmovil.containers.marco_Container

class marco_ContainerAdapter(
    context: Context,
    private val containers: MutableList<marco_Container>,
    private val listener: OnContainerActionListener
) : ArrayAdapter<marco_Container>(context, 0, containers) {

    interface OnContainerActionListener {
        fun onAssignClicked(container: marco_Container)
        fun onReleaseClicked(container: marco_Container)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_marco_container, parent, false)

        val container = containers[position]

        val txtContainerId = view.findViewById<TextView>(R.id.txtContainerId)
        val txtProduct = view.findViewById<TextView>(R.id.txtProduct)
        val txtStatus = view.findViewById<TextView>(R.id.txtStatus)
        val btnAction = view.findViewById<Button>(R.id.btnAction)

        txtContainerId.text = "ID: ${container.id}"
        txtProduct.text = "Product: ${container.product}"

        val isFree = container.technician.isNullOrEmpty()
        if (isFree) {
            txtStatus.text = "Status: FREE"
            btnAction.text = "ASSIGN"
        } else {
            txtStatus.text = "Status: ASSIGNED to ${container.technician}"
            btnAction.text = "RELEASE"
        }

        btnAction.setOnClickListener {
            if (isFree) {
                listener.onAssignClicked(container)
            } else {
                listener.onReleaseClicked(container)
            }
        }

        return view
    }

    fun replaceAll(newItems: List<marco_Container>) {
        containers.clear()
        containers.addAll(newItems)
        notifyDataSetChanged()
    }
}
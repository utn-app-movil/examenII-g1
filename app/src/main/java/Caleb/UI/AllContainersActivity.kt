package Caleb.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import Caleb.ServiceC.ApiServiceCaleb
import Caleb.modelC.ContainerData
import Caleb.modelC.ContainersListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import cr.ac.utn.appmovil.containers.R

class AllContainersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_containers_caleb)

        val container: LinearLayout = findViewById(R.id.containerItemsScrollable)

        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) { ApiServiceCaleb.api.getContainers() }
                if (response.isSuccessful) {
                    val body: ContainersListResponse? = response.body()
                    val items: List<ContainerData> = body?.data ?: emptyList()

                    container.removeAllViews()
                    val inflater = LayoutInflater.from(this@AllContainersActivity)
                    for (item in items) {
                        val itemView = inflater.inflate(R.layout.item_container_caleb, container, false)
                        val tvId = itemView.findViewById<TextView>(R.id.tvContainerId)
                        val tvProduct = itemView.findViewById<TextView>(R.id.tvContainerProduct)
                        val tvTech = itemView.findViewById<TextView>(R.id.tvContainerTechnician)
                        val tvDate = itemView.findViewById<TextView>(R.id.tvContainerDate)
                        tvId.text = item.id ?: ""
                        tvProduct.text = item.product ?: ""
                        tvTech.text = item.technician ?: ""
                        tvDate.text = item.date ?: ""
                        container.addView(itemView)
                    }
                } else {
                    Toast.makeText(this@AllContainersActivity, "Error: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@AllContainersActivity, "Fallo de red: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }
}

package cr.ac.utn.appmovil.containers

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import adapter.luis_ContainerListAdapter
import Controller.luis_ContainerController
import interfaces.luis_IOnContainerClickListener
import kotlinx.coroutines.launch
import model.luis_DTOContainer

class luis_listContainersActivity : AppCompatActivity(), luis_IOnContainerClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var containerController: luis_ContainerController
    private lateinit var customAdapter: luis_ContainerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.luis_activity_listcontainers)

        recyclerView = findViewById(R.id.rvContainers)
        containerController = luis_ContainerController(this)

        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager

        loadContainers()
    }

    private fun loadContainers() {
        lifecycleScope.launch {
            try {
                Toast.makeText(
                    this@luis_listContainersActivity,
                    getString(R.string.message_loading_containers),
                    Toast.LENGTH_SHORT
                ).show()

                val containers = containerController.getContainers()

                customAdapter = luis_ContainerListAdapter(
                    containers,
                    this@luis_listContainersActivity
                )
                recyclerView.adapter = customAdapter
                customAdapter.notifyDataSetChanged()

                Toast.makeText(
                    this@luis_listContainersActivity,
                    getString(R.string.message_containers_loaded, containers.size),
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: Exception) {
                val errorMsg = e.message ?: getString(R.string.error_load_containers_fallback)

                Toast.makeText(
                    this@luis_listContainersActivity,
                    getString(R.string.error_load_containers_toast, errorMsg),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onContainerClicked(container: luis_DTOContainer) {
        Toast.makeText(
            this,
            getString(R.string.container_selected, container.Id),
            Toast.LENGTH_SHORT
        ).show()
    }
}

package cr.ac.utn.appmovil.containers

import Controller.ricar_ContainerAdapter
import Service.ricar_APIService
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import model.ricar_AssignRequest
import model.ricar_ReleaseRequest


class ricar_ContainersActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshButton: Button
    private lateinit var containerAdapter: ricar_ContainerAdapter
    private var technicianEmail: String? = null
    private var currentContainers: MutableList<ricar_Container> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ricar_activity_containers)

        technicianEmail = intent.getStringExtra("TECHNICIAN_EMAIL")

        recyclerView = findViewById(R.id.recyclerView)
        refreshButton = findViewById(R.id.refreshButton)

        setupRecyclerView()

        refreshButton.setOnClickListener {
            fetchContainers(true)
        }

        fetchContainers()
    }

    private fun setupRecyclerView() {
        containerAdapter = ricar_ContainerAdapter(
            containers = currentContainers,
            onAssign = { container -> assignContainerToTechnician(container) },
            onRelease = { container -> releaseContainer(container) }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = containerAdapter
    }

    private fun fetchContainers(isManualRefresh: Boolean = false) {
        lifecycleScope.launch {
            try {
                val response = ricar_APIService.api.getContainers()
                if (response.isSuccessful && response.body() != null) {
                    currentContainers.clear()
                    currentContainers.addAll(response.body()!!.data)
                    containerAdapter.notifyDataSetChanged()
                    if (isManualRefresh) {
                        Toast.makeText(this@ricar_ContainersActivity, "Lista actualizada", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "sin detalles"
                    Toast.makeText(this@ricar_ContainersActivity, "Error al cargar: ${response.code()} - $errorBody", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ricar_ContainersActivity, "Error de red: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun assignContainerToTechnician(container: ricar_Container) {
        technicianEmail?.let { email ->
            lifecycleScope.launch {
                try {
                    val request = ricar_AssignRequest(container.id, email)
                    val response = ricar_APIService.api.assignContainer(request)
                    if (response.isSuccessful) {
                        Toast.makeText(this@ricar_ContainersActivity, "Contenedor asignado correctamente", Toast.LENGTH_SHORT).show()

                        val index = currentContainers.indexOfFirst { it.id == container.id }
                        if (index != -1) {
                            currentContainers[index] = container.copy(technician = email)
                            containerAdapter.notifyItemChanged(index)
                        }

                    } else {
                        val errorBody = response.errorBody()?.string() ?: "sin detalles"
                        Toast.makeText(this@ricar_ContainersActivity, "Fallo al asignar: ${response.code()} - $errorBody", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@ricar_ContainersActivity, "Error de red: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        } ?: Toast.makeText(this, "Error: no se encontró el email del técnico", Toast.LENGTH_SHORT).show()
    }

    private fun releaseContainer(container: ricar_Container) {
        lifecycleScope.launch {
            try {
                val request = ricar_ReleaseRequest(container.id)
                val response = ricar_APIService.api.releaseContainer(request)
                if (response.isSuccessful) {
                    Toast.makeText(this@ricar_ContainersActivity, "Contenedor liberado correctamente", Toast.LENGTH_SHORT).show()

                    val index = currentContainers.indexOfFirst { it.id == container.id }
                    if (index != -1) {
                        currentContainers[index] = container.copy(technician = null)
                        containerAdapter.notifyItemChanged(index)
                    }

                } else {
                    val errorBody = response.errorBody()?.string() ?: "sin detalles"
                    Toast.makeText(this@ricar_ContainersActivity, "Fallo al liberar: ${response.code()} - $errorBody", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ricar_ContainersActivity, "Error de red: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
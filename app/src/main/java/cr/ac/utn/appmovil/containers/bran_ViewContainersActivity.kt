package cr.ac.utn.appmovil.containers

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.appmovil.containers.models.bran_AssignContainerRequest
import cr.ac.utn.appmovil.containers.models.bran_ReleaseContainerRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class bran_ViewContainersActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: bran_ContainerAdapter
    private lateinit var btnRefresh: Button   // ← botón agregado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bran_activity_view_containers)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        btnRefresh = findViewById(R.id.btnRefresh)   // ← referencia al botón

        // Acción del botón Refrescar
        btnRefresh.setOnClickListener {
            fetchContainers()
            Toast.makeText(this, "Actualizando...", Toast.LENGTH_SHORT).show()
        }

        fetchContainers()
    }

    private fun fetchContainers() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = bran_RetrofitClient.apiService.getContainers()
                if (response.isSuccessful) {
                    val containerResponse = response.body()
                    containerResponse?.let {
                        adapter = bran_ContainerAdapter(
                            it.data.toMutableList(),
                            onAssignClick = { container -> assignContainer(container.id) },
                            onReleaseClick = { container -> releaseContainer(container.id) }
                        )
                        recyclerView.adapter = adapter
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@bran_ViewContainersActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun assignContainer(containerId: String) {

        val prefs = getSharedPreferences("APP_PREFS", MODE_PRIVATE)
        val technicianEmail = prefs.getString("USER_EMAIL", null)

        if (technicianEmail == null) {
            Toast.makeText(this, "Error: usuario no logueado", Toast.LENGTH_SHORT).show()
            return
        }

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val request = AssignRequest(
                    id = containerId,
                    technician = technicianEmail
                )

                val response = bran_RetrofitClient.apiService.assignContainer(request)

                if (response.isSuccessful) {
                    Toast.makeText(this@bran_ViewContainersActivity, "Contenedor asignado", Toast.LENGTH_SHORT).show()
                    fetchContainers()
                } else {
                    Toast.makeText(this@bran_ViewContainersActivity, "Error al asignar", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@bran_ViewContainersActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun releaseContainer(containerId: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val request = ReleaseRequest(id = containerId)
                val response = bran_RetrofitClient.apiService.releaseContainer(request)

                if (response.isSuccessful) {
                    Toast.makeText(this@bran_ViewContainersActivity, "Contenedor liberado", Toast.LENGTH_SHORT).show()
                    fetchContainers()
                } else {
                    Toast.makeText(this@bran_ViewContainersActivity, "Error al liberar", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@bran_ViewContainersActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

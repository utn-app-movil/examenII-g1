package cr.ac.utn.appmovil.containers

import Service.sam_APIService
import adapter.sam_ContainerAdapter
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.sam_AssignContainerRequest
import model.sam_Container
import model.sam_ReleaseContainerRequest

class sam_ContainerListActivity : AppCompatActivity() {

    private lateinit var btnAddCont: Button
    private lateinit var btnRefresh: Button
    private lateinit var rvContainers: RecyclerView
    private lateinit var adapter: sam_ContainerAdapter

    private var loggedTechnicianEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sam_container_list)

        // Email del técnico logueado (viene desde AuthenticationActivity)
        loggedTechnicianEmail = intent.getStringExtra("EXTRA_TECH_EMAIL") ?: ""

        btnAddCont = findViewById(R.id.btnAddContainer)
        btnRefresh = findViewById(R.id.btnRefresh)
        rvContainers = findViewById(R.id.rvContainers)

        adapter = sam_ContainerAdapter(
            mutableListOf(),
            onAssignClick = { container -> assignContainer(container) },
            onReleaseClick = { container -> releaseContainer(container) }
        )

        rvContainers.layoutManager = LinearLayoutManager(this)
        rvContainers.adapter = adapter

        btnAddCont.setOnClickListener {
            startActivity(
                android.content.Intent(this, sam_AddContainerActivity::class.java)
            )
        }

        btnRefresh.setOnClickListener {
            loadContainers()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sam_container_list_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Load at login
        loadContainers()
    }

    // Loads all containers in DB
    private fun loadContainers() {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    sam_APIService.api.getContainers()
                }

                if (response.isSuccessful) {
                    val body = response.body()
                    val data: List<sam_Container>? = body?.data

                    if (!data.isNullOrEmpty()) {
                        adapter.replaceAll(data)
                    } else {
                        Toast.makeText(
                            this@sam_ContainerListActivity,
                            body?.message ?: "No se encontraron contenedores",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@sam_ContainerListActivity,
                        "Error HTTP: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@sam_ContainerListActivity,
                    "Error al cargar contenedores: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // Assigns a Technician to a container
    private fun assignContainer(container: sam_Container) {
        if (loggedTechnicianEmail.isEmpty()) {
            Toast.makeText(this, "No se encontró el email del técnico logueado", Toast.LENGTH_LONG).show()
            return
        }

        val request = sam_AssignContainerRequest(
            id = container.id,
            technician = loggedTechnicianEmail
        )

        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    sam_APIService.api.assignContainer(request)
                }

                if (response.isSuccessful) {
                    Toast.makeText(
                        this@sam_ContainerListActivity,
                        response.body()?.message ?: "Contenedor asignado",
                        Toast.LENGTH_LONG
                    ).show()
                    loadContainers()
                } else {
                    Toast.makeText(
                        this@sam_ContainerListActivity,
                        "Error HTTP al asignar: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@sam_ContainerListActivity,
                    "Error al asignar: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // Releases a container from a technician
    private fun releaseContainer(container: sam_Container) {
        val request = sam_ReleaseContainerRequest(
            id = container.id
        )

        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    sam_APIService.api.releaseContainer(request)
                }

                if (response.isSuccessful) {
                    Toast.makeText(
                        this@sam_ContainerListActivity,
                        response.body()?.message ?: "Contenedor liberado",
                        Toast.LENGTH_LONG
                    ).show()
                    loadContainers()
                } else {
                    Toast.makeText(
                        this@sam_ContainerListActivity,
                        "Error HTTP al liberar: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@sam_ContainerListActivity,
                    "Error al liberar: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

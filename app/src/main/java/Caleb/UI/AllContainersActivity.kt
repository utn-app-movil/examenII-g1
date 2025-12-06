package Caleb.UI

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import Caleb.ServiceC.ApiServiceCaleb
import Caleb.modelC.ContainerAssignRequest
import Caleb.modelC.ContainerCreateResponse
import Caleb.modelC.ContainerData
import Caleb.modelC.ContainerReleaseRequest
import Caleb.modelC.ContainersListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import cr.ac.utn.appmovil.containers.R

class AllContainersActivity : AppCompatActivity() {
    private lateinit var container: LinearLayout
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_containers_caleb)
        container = findViewById(R.id.containerItemsScrollable)
        loadContainers()
    }

    private fun loadContainers() {
        if (isLoading) return
        isLoading = true
        lifecycleScope.launch {
            try {
                val items = fetchContainersWithRetry(maxAttempts = 3)
                container.removeAllViews()
                val inflater = LayoutInflater.from(this@AllContainersActivity)
                for (item in items) {
                    val itemView = inflater.inflate(R.layout.item_container_caleb, container, false)
                    val tvId = itemView.findViewById<TextView>(R.id.tvContainerId)
                    val tvProduct = itemView.findViewById<TextView>(R.id.tvContainerProduct)
                    val tvTech = itemView.findViewById<TextView>(R.id.tvContainerTechnician)
                    val tvDate = itemView.findViewById<TextView>(R.id.tvContainerDate)
                    val btnAssign = itemView.findViewById<Button>(R.id.btnAssign)
                    val btnRelease = itemView.findViewById<Button>(R.id.btnRelease)

                    tvId.text = item.id ?: ""
                    tvProduct.text = item.product ?: ""
                    tvTech.text = item.technician ?: ""
                    tvDate.text = item.date ?: ""

                    val isOccupied = !item.technician.isNullOrEmpty()
                    btnAssign.visibility = if (isOccupied) android.view.View.GONE else android.view.View.VISIBLE
                    btnRelease.visibility = if (isOccupied) android.view.View.VISIBLE else android.view.View.GONE

                    btnAssign.setOnClickListener {
                        btnAssign.isEnabled = false
                        assignContainer(item) { btnAssign.isEnabled = true }
                    }
                    btnRelease.setOnClickListener {
                        btnRelease.isEnabled = false
                        releaseContainer(item) { btnRelease.isEnabled = true }
                    }

                    container.addView(itemView)
                }
            } catch (e: Exception) {
                Toast.makeText(this@AllContainersActivity, "Error refrescando: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            } finally {
                isLoading = false
            }
        }
    }

    private suspend fun fetchContainersWithRetry(maxAttempts: Int): List<ContainerData> {
        var attempt = 0
        var lastError: Throwable? = null
        while (attempt < maxAttempts) {
            try {
                val response = withContext(Dispatchers.IO) { ApiServiceCaleb.api.getContainers() }
                if (response.isSuccessful) {
                    val body: ContainersListResponse? = response.body()
                    return body?.data ?: emptyList()
                } else {
                    // Si 5xx, intentamos de nuevo con backoff
                    if (response.code() in 500..599) {
                        attempt++
                        val backoff = 300L * attempt
                        delay(backoff)
                        continue
                    } else {
                        throw RuntimeException("HTTP ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                lastError = e
                attempt++
                delay(300L * attempt)
            }
        }
        throw lastError ?: RuntimeException("Fallo desconocido al cargar contenedores")
    }

    private fun assignContainer(item: ContainerData, onDone: () -> Unit) {
        val email = getSharedPreferences("caleb_prefs", MODE_PRIVATE).getString("user", null)
        if (email.isNullOrEmpty()) {
            Toast.makeText(this, getString(R.string.caleb_no_session), Toast.LENGTH_SHORT).show()
            onDone()
            return
        }
        lifecycleScope.launch {
            try {
                val resp = withContext(Dispatchers.IO) {
                    ApiServiceCaleb.api.assignContainer(ContainerAssignRequest(id = item.id ?: "", technician = email))
                }
                val body: ContainerCreateResponse? = resp.body()
                if (resp.isSuccessful && body?.responseCode == "SUCESSFUL") {
                    Toast.makeText(this@AllContainersActivity, body.message ?: getString(R.string.caleb_assign), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@AllContainersActivity, body?.message ?: "Error: ${resp.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@AllContainersActivity, "Fallo de red: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            } finally {
                onDone()
                // Pequeño delay para dar tiempo al backend antes de refrescar
                Handler(Looper.getMainLooper()).postDelayed({ loadContainers() }, 400)
            }
        }
    }

    private fun releaseContainer(item: ContainerData, onDone: () -> Unit) {
        lifecycleScope.launch {
            try {
                val resp = withContext(Dispatchers.IO) {
                    ApiServiceCaleb.api.releaseContainer(ContainerReleaseRequest(id = item.id ?: ""))
                }
                val body: ContainerCreateResponse? = resp.body()
                if (resp.isSuccessful && body?.responseCode == "SUCESSFUL") {
                    Toast.makeText(this@AllContainersActivity, body.message ?: getString(R.string.caleb_release), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@AllContainersActivity, body?.message ?: "Error: ${resp.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@AllContainersActivity, "Fallo de red: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            } finally {
                onDone()
                Handler(Looper.getMainLooper()).postDelayed({ loadContainers() }, 400)
            }
        }
    }
}

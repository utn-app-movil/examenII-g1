package cr.ac.utn.appmovil.containers

import Service.bray_APIService
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class bray_ContainerListActivity : AppCompatActivity() {

    private lateinit var rvContainers: RecyclerView
    private lateinit var btnRefresh: Button
    private lateinit var btnCreate: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: bray_ContainerAdapter
    private var userEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bray_activity_container_list)

        userEmail = intent.getStringExtra("USER_EMAIL") ?: ""

        rvContainers = findViewById(R.id.rvContainers)
        btnRefresh = findViewById(R.id.btnRefresh)
        btnCreate = findViewById(R.id.btnCreate)
        progressBar = findViewById(R.id.progressBar)

        rvContainers.layoutManager = LinearLayoutManager(this)
        adapter = bray_ContainerAdapter(emptyList(),
            onAssignClick = { container -> assignContainer(container) },
            onReleaseClick = { container -> releaseContainer(container) }
        )
        rvContainers.adapter = adapter

        btnRefresh.setOnClickListener {
            loadContainers()
        }

        btnCreate.setOnClickListener {
            val intent = Intent(this, bray_ContainerCreatorActivity::class.java)
            startActivity(intent)
        }

        loadContainers()
    }

    private fun loadContainers() {
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = bray_APIService.api.getContainers()
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful && response.body() != null) {
                        val listResponse = response.body()!!
                        if (listResponse.data != null) {
                            adapter.updateData(listResponse.data)
                        } else {
                            Toast.makeText(this@bray_ContainerListActivity, listResponse.message, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@bray_ContainerListActivity, getString(R.string.error_loading_containers), Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        this@bray_ContainerListActivity,
                        getString(R.string.error_connection_message, e.message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun assignContainer(container: bray_ContainerData) {
        if (userEmail.isEmpty()) {
            Toast.makeText(this@bray_ContainerListActivity, getString(R.string.error_user_not_identified), Toast.LENGTH_SHORT).show()
            return
        }

        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = bray_AssignContainerRequest(container.id, userEmail)
                val response = bray_APIService.api.assignContainer(request)
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        Toast.makeText(this@bray_ContainerListActivity, getString(R.string.container_assigned_success), Toast.LENGTH_SHORT).show()
                        loadContainers() // Reload list
                    } else {
                        Toast.makeText(this@bray_ContainerListActivity, getString(R.string.error_assigning_container), Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        this@bray_ContainerListActivity,
                        getString(R.string.error_general_message, e.message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun releaseContainer(container: bray_ContainerData) {
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = bray_ReleaseContainerRequest(container.id)
                val response = bray_APIService.api.releaseContainer(request)
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        Toast.makeText(this@bray_ContainerListActivity, getString(R.string.container_released_success), Toast.LENGTH_SHORT).show()
                        loadContainers()
                    } else {
                        Toast.makeText(this@bray_ContainerListActivity, getString(R.string.error_releasing_container), Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        this@bray_ContainerListActivity,
                        getString(R.string.error_general_message, e.message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
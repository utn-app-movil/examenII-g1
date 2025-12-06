package cr.ac.utn.appmovil.containers

import Service.DougAPIClient
import adapter.DougContainerAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.DougAssignRequest
import model.DougContainer
import model.DougReleaseRequest

class DougContainerListActivity : AppCompatActivity() {

    private lateinit var dougRecyclerView: RecyclerView
    private lateinit var dougProgressBar: ProgressBar
    private lateinit var dougRefreshButton: Button
    private lateinit var dougFabCreate: FloatingActionButton
    private lateinit var dougAdapter: DougContainerAdapter
    private var dougUserEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.doug_container_list)

        dougUserEmail = getUserEmail()
        initializeViews()
        setupRecyclerView()
        setupClickListeners()
        loadContainers()
    }

    override fun onResume() {
        super.onResume()
        loadContainers()
    }

    private fun getUserEmail(): String {
        val sharedPref = getSharedPreferences("DougPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("doug_user_email", "") ?: ""
    }

    private fun initializeViews() {
        dougRecyclerView = findViewById(R.id.doug_recycler_view)
        dougProgressBar = findViewById(R.id.doug_list_progress_bar)
        dougRefreshButton = findViewById(R.id.doug_refresh_button)
        dougFabCreate = findViewById(R.id.doug_fab_create)
    }

    private fun setupRecyclerView() {
        dougAdapter = DougContainerAdapter(
            containers = emptyList(),
            onAssignClick = { container -> assignContainer(container) },
            onReleaseClick = { container -> releaseContainer(container) },
            currentUserEmail = dougUserEmail
        )

        dougRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@DougContainerListActivity)
            adapter = dougAdapter
        }
    }

    private fun setupClickListeners() {
        dougRefreshButton.setOnClickListener {
            loadContainers()
        }

        dougFabCreate.setOnClickListener {
            val intent = Intent(this, DougCreateContainerActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadContainers() {
        showLoading(true)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = DougAPIClient.apiService.getContainers()

                withContext(Dispatchers.Main) {
                    showLoading(false)

                    if (response.isSuccessful) {
                        val containerResponse = response.body()

                        if (containerResponse != null && containerResponse.responseCode == "SUCESSFUL") {
                            val containers = containerResponse.data ?: emptyList()
                            dougAdapter.updateContainers(containers)
                        } else {
                            Toast.makeText(
                                this@DougContainerListActivity,
                                containerResponse?.message ?: getString(R.string.doug_load_failed),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@DougContainerListActivity,
                            getString(R.string.doug_error_network),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    Toast.makeText(
                        this@DougContainerListActivity,
                        "${getString(R.string.doug_error_exception)}: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun assignContainer(container: DougContainer) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = DougAssignRequest(container.id, dougUserEmail)
                val response = DougAPIClient.apiService.assignContainer(request)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val assignResponse = response.body()

                        if (assignResponse != null && assignResponse.responseCode == "SUCESSFUL") {
                            Toast.makeText(
                                this@DougContainerListActivity,
                                getString(R.string.doug_container_assigned),
                                Toast.LENGTH_SHORT
                            ).show()
                            loadContainers()
                        } else {
                            Toast.makeText(
                                this@DougContainerListActivity,
                                assignResponse?.message ?: getString(R.string.doug_assign_failed),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@DougContainerListActivity,
                            getString(R.string.doug_error_network),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@DougContainerListActivity,
                        "${getString(R.string.doug_error_exception)}: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun releaseContainer(container: DougContainer) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = DougReleaseRequest(container.id)
                val response = DougAPIClient.apiService.releaseContainer(request)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val releaseResponse = response.body()

                        if (releaseResponse != null && releaseResponse.responseCode == "SUCESSFUL") {
                            Toast.makeText(
                                this@DougContainerListActivity,
                                getString(R.string.doug_container_released),
                                Toast.LENGTH_SHORT
                            ).show()
                            loadContainers()
                        } else {
                            Toast.makeText(
                                this@DougContainerListActivity,
                                releaseResponse?.message ?: getString(R.string.doug_release_failed),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@DougContainerListActivity,
                            getString(R.string.doug_error_network),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@DougContainerListActivity,
                        "${getString(R.string.doug_error_exception)}: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        dougProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        dougRefreshButton.isEnabled = !isLoading
    }
}
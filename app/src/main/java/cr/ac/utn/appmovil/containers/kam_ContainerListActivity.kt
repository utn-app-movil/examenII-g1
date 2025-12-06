package cr.ac.utn.appmovil.containers

import Service.kam_RetrofitClient
import adapter.kam_ContainerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.appmovil.containers.model.kam_Container
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import util.util

class kam_ContainerListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: kam_ContainerAdapter
    private lateinit var btnRefresh: Button
    private lateinit var btnCreateNew: Button
    private lateinit var progressBar: ProgressBar
    private var userEmail: String = ""

    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kam_container_list)

        loadUserEmail()
        initViews()
        setupRecyclerView()
        setupListeners()
        loadContainers()
    }

    private fun loadUserEmail() {
        val sharedPref = getSharedPreferences("kam_user_prefs", MODE_PRIVATE)
        userEmail = sharedPref.getString("user_email", "") ?: ""
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.kam_recycler_view)
        btnRefresh = findViewById(R.id.kam_btn_refresh)
        btnCreateNew = findViewById(R.id.kam_btn_create_new)
        progressBar = findViewById(R.id.kam_progress_bar)
    }

    private fun setupRecyclerView() {
        adapter = kam_ContainerAdapter(
            containers = emptyList(),
            onAssign = { container -> assignContainer(container) },
            onRelease = { container -> releaseContainer(container) }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupListeners() {

        btnRefresh.setOnClickListener {
            Toast.makeText(this, "REFRESH presionado", Toast.LENGTH_SHORT).show()
            loadContainers()
        }

        btnCreateNew.setOnClickListener {
            Toast.makeText(this, "CREATE presionado", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, kam_CreateContainerActivity::class.java)
            startActivity(intent)
        }


}

    override fun onResume() {
        super.onResume()
        loadContainers()
    }

    private fun loadContainers() {
        showLoading(true)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = kam_RetrofitClient.apiService.getContainers()

                withContext(Dispatchers.Main) {
                    showLoading(false)

                    if (response.isSuccessful) {
                        val apiResponse = response.body()

                        if (apiResponse != null && apiResponse.responseCode == "SUCESSFUL") {
                            val containers = apiResponse.data ?: emptyList()
                            adapter.updateContainers(containers)
                        } else {
                            Toast.makeText(
                                this@kam_ContainerListActivity,
                                apiResponse?.message ?: getString(R.string.kam_load_error),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@kam_ContainerListActivity,
                            R.string.kam_load_error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    Toast.makeText(
                        this@kam_ContainerListActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun assignContainer(container: kam_Container) {
        util.showDialogCondition(
            context = this,
            titleQuestion = "Confirm",
            questionText = getString(R.string.kam_confirm_assign),
            positiveStr = getString(R.string.kam_ok),
            negativeStr = getString(R.string.kam_cancel),
            positiveCallback = { performAssign(container) },
            negativeCallback = { }
        )
    }

    private fun performAssign(container: kam_Container) {
        showLoading(true)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val assignData = mapOf(
                    "id" to container.id,
                    "technician" to userEmail
                )

                val response = kam_RetrofitClient.apiService.assignContainer(assignData)

                withContext(Dispatchers.Main) {
                    showLoading(false)

                    if (response.isSuccessful) {
                        val apiResponse = response.body()

                        if (apiResponse != null && apiResponse.responseCode == "SUCESSFUL") {
                            Toast.makeText(
                                this@kam_ContainerListActivity,
                                R.string.kam_assign_success,
                                Toast.LENGTH_SHORT
                            ).show()
                            loadContainers()
                        } else {
                            Toast.makeText(
                                this@kam_ContainerListActivity,
                                apiResponse?.message ?: getString(R.string.kam_assign_error),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@kam_ContainerListActivity,
                            R.string.kam_assign_error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    Toast.makeText(
                        this@kam_ContainerListActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun releaseContainer(container: kam_Container) {
        util.showDialogCondition(
            context = this,
            titleQuestion = "Confirm",
            questionText = getString(R.string.kam_confirm_release),
            positiveStr = getString(R.string.kam_ok),
            negativeStr = getString(R.string.kam_cancel),
            positiveCallback = { performRelease(container) },
            negativeCallback = { }
        )
    }

    private fun performRelease(container: kam_Container) {
        showLoading(true)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val releaseData = mapOf(
                    "id" to container.id
                )

                val response = kam_RetrofitClient.apiService.releaseContainer(releaseData)

                withContext(Dispatchers.Main) {
                    showLoading(false)

                    if (response.isSuccessful) {
                        val apiResponse = response.body()

                        if (apiResponse != null && apiResponse.responseCode == "SUCESSFUL") {
                            Toast.makeText(
                                this@kam_ContainerListActivity,
                                R.string.kam_release_success,
                                Toast.LENGTH_SHORT
                            ).show()
                            loadContainers()
                        } else {
                            Toast.makeText(
                                this@kam_ContainerListActivity,
                                apiResponse?.message ?: getString(R.string.kam_release_error),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@kam_ContainerListActivity,
                            R.string.kam_release_error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    Toast.makeText(
                        this@kam_ContainerListActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }
}
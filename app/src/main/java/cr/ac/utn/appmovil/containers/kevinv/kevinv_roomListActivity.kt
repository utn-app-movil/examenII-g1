package cr.ac.utn.appmovil.containers.kevinv

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.appmovil.containers.R
import kotlinx.coroutines.*

class kevinv_roomListActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var progress: ProgressBar
    private lateinit var btnRefresh: Button
    private lateinit var adapter: kevinv_ContainerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kevinv_room_list)

        recycler = findViewById(R.id.kevinv_recyclerContainers)
        progress = findViewById(R.id.kevinv_progressList)
        btnRefresh = findViewById(R.id.kevinv_btnRefresh)

        adapter = kevinv_ContainerAdapter(
            mutableListOf(),
            onAssignClick = { container -> kevinv_assignContainer(container) },
            onReleaseClick = { container -> kevinv_releaseContainer(container) }
        )

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        btnRefresh.setOnClickListener { kevinv_loadContainers() }

        kevinv_loadContainers()
    }

    private fun kevinv_loadContainers() {
        progress.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = kevinv_ApiClient.api.kevinv_getContainers()

                withContext(Dispatchers.Main) {
                    progress.visibility = View.GONE

                    if (response.responseCode == "SUCESSFUL") {
                        val list = response.data ?: emptyList()
                        adapter.updateData(list)
                    } else {
                        Toast.makeText(
                            this@kevinv_roomListActivity,
                            response.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    progress.visibility = View.GONE
                    Toast.makeText(
                        this@kevinv_roomListActivity,
                        ex.message ?: "Unexpected error",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun kevinv_assignContainer(container: kevinv_Container) {
        val email = kevinv_SessionManager.loggedUserEmail
        if (email.isNullOrEmpty()) {
            Toast.makeText(this, "No logged user email", Toast.LENGTH_LONG).show()
            return
        }

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = kevinv_ApiClient.api.kevinv_assignContainer(
                    kevinv_AssignContainerRequest(container.id, email)
                )

                withContext(Dispatchers.Main) {
                    if (response.responseCode == "SUCESSFUL") {
                        Toast.makeText(
                            this@kevinv_roomListActivity,
                            response.message,
                            Toast.LENGTH_LONG
                        ).show()
                        kevinv_loadContainers()
                    } else {
                        Toast.makeText(
                            this@kevinv_roomListActivity,
                            response.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@kevinv_roomListActivity,
                        ex.message ?: "Unexpected error",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun kevinv_releaseContainer(container: kevinv_Container) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = kevinv_ApiClient.api.kevinv_releaseContainer(
                    kevinv_ReleaseContainerRequest(container.id)
                )

                withContext(Dispatchers.Main) {
                    if (response.responseCode == "SUCESSFUL") {
                        Toast.makeText(
                            this@kevinv_roomListActivity,
                            response.message,
                            Toast.LENGTH_LONG
                        ).show()
                        kevinv_loadContainers()
                    } else {
                        Toast.makeText(
                            this@kevinv_roomListActivity,
                            response.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@kevinv_roomListActivity,
                        ex.message ?: "Unexpected error",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}

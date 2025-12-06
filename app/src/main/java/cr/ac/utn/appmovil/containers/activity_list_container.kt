package cr.ac.utn.appmovil.containers

import adapter.marco_ContainerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class activity_list_container : AppCompatActivity(),
    marco_ContainerAdapter.OnContainerActionListener {

    private lateinit var txtLoggedUser: TextView
    private lateinit var btnGoToCreate: Button
    private lateinit var btnRefresh: Button
    private lateinit var progressContainers: ProgressBar
    private lateinit var lvContainers: ListView

    private lateinit var adapter: marco_ContainerAdapter
    private val containersList: MutableList<marco_Container> = mutableListOf()

    private var loggedUsername: String? = null
    private var loggedTechnicianEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_container)

        txtLoggedUser = findViewById(R.id.txtLoggedUser)
        btnGoToCreate = findViewById(R.id.btnGoToCreate)
        btnRefresh = findViewById(R.id.btnRefresh)
        progressContainers = findViewById(R.id.progressContainers)
        lvContainers = findViewById(R.id.lvContainers)

        adapter = marco_ContainerAdapter(this, containersList, this)
        lvContainers.adapter = adapter

        loggedUsername =
            intent.getStringExtra(marco_authenticationActivity.EXTRA_LOGGED_USERNAME)
        txtLoggedUser.text = "Logged user: ${loggedUsername ?: "Unknown"}"

        btnGoToCreate.setOnClickListener {
            val intent = Intent(this, marco_activityContainer::class.java)
            startActivity(intent)
        }

        btnRefresh.setOnClickListener {
            refreshData()
        }
        refreshData()
    }

    private fun refreshData() {
        progressContainers.visibility = View.VISIBLE

        marco_AuthApiClient.service.getTechnicians()
            .enqueue(object : Callback<marco_TechniciansResponse> {
                override fun onResponse(
                    call: Call<marco_TechniciansResponse>,
                    response: Response<marco_TechniciansResponse>
                ) {
                    if (!response.isSuccessful) {
                        progressContainers.visibility = View.GONE
                        Toast.makeText(
                            this@activity_list_container,
                            getString(R.string.marco_error_technicians),
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }

                    val body = response.body()
                    val list = body?.data.orEmpty()

                    // Buscar técnico por ID (TEC-02, TEC-03, etc.)
                    val tech = list.firstOrNull { it.id == loggedUsername }
                    loggedTechnicianEmail = tech?.email

                    if (loggedTechnicianEmail == null) {
                        Toast.makeText(
                            this@activity_list_container,
                            "Could not find technician email for logged user",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    // Ahora sí, cargar contenedores
                    loadContainers()
                }

                override fun onFailure(
                    call: Call<marco_TechniciansResponse>,
                    t: Throwable
                ) {
                    progressContainers.visibility = View.GONE
                    Toast.makeText(
                        this@activity_list_container,
                        t.localizedMessage
                            ?: getString(R.string.marco_error_technicians),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    // 2) GET /containers
    private fun loadContainers() {
        marco_ContainerApiClient.service.getContainers()
            .enqueue(object : Callback<marco_ContainerListResponse> {
                override fun onResponse(
                    call: Call<marco_ContainerListResponse>,
                    response: Response<marco_ContainerListResponse>
                ) {
                    progressContainers.visibility = View.GONE

                    if (!response.isSuccessful) {
                        Toast.makeText(
                            this@activity_list_container,
                            "Error getting containers (HTTP ${response.code()})",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }

                    val body = response.body()
                    val list = body?.data ?: emptyList()

                    adapter.replaceAll(list)
                }

                override fun onFailure(
                    call: Call<marco_ContainerListResponse>,
                    t: Throwable
                ) {
                    progressContainers.visibility = View.GONE
                    Toast.makeText(
                        this@activity_list_container,
                        t.localizedMessage ?: "Error getting containers",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    override fun onAssignClicked(container: marco_Container) {
        val email = loggedTechnicianEmail
        if (email.isNullOrEmpty()) {
            Toast.makeText(
                this,
                "Technician email not available",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val request = marco_AssignContainerRequest(
            id = container.id,
            technician = email
        )

        marco_ContainerApiClient.service.assignContainer(request)
            .enqueue(object : Callback<marco_ContainerResponse> {
                override fun onResponse(
                    call: Call<marco_ContainerResponse>,
                    response: Response<marco_ContainerResponse>
                ) {
                    if (!response.isSuccessful) {
                        Toast.makeText(
                            this@activity_list_container,
                            "Error assigning container (HTTP ${response.code()})",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }

                    val body = response.body()
                    if (body == null) {
                        Toast.makeText(
                            this@activity_list_container,
                            "Error assigning container",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }

                    Toast.makeText(
                        this@activity_list_container,
                        body.message,
                        Toast.LENGTH_LONG
                    ).show()

                    refreshData()
                }

                override fun onFailure(
                    call: Call<marco_ContainerResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        this@activity_list_container,
                        t.localizedMessage ?: "Error assigning container",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    override fun onReleaseClicked(container: marco_Container) {
        val request = marco_ReleaseContainerRequest(id = container.id)

        marco_ContainerApiClient.service.releaseContainer(request)
            .enqueue(object : Callback<marco_ContainerResponse> {
                override fun onResponse(
                    call: Call<marco_ContainerResponse>,
                    response: Response<marco_ContainerResponse>
                ) {
                    if (!response.isSuccessful) {
                        Toast.makeText(
                            this@activity_list_container,
                            "Error releasing container (HTTP ${response.code()})",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }

                    val body = response.body()
                    if (body == null) {
                        Toast.makeText(
                            this@activity_list_container,
                            "Error releasing container",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }

                    Toast.makeText(
                        this@activity_list_container,
                        body.message,
                        Toast.LENGTH_LONG
                    ).show()

                    refreshData()
                }

                override fun onFailure(
                    call: Call<marco_ContainerResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        this@activity_list_container,
                        t.localizedMessage ?: "Error releasing container",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
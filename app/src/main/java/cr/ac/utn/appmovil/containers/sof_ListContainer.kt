package cr.ac.utn.appmovil.containers

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.appmovil.containers.Service.sof_apiService
import cr.ac.utn.appmovil.containers.model.ApiResponse
import cr.ac.utn.appmovil.containers.model.AssignRequest
import cr.ac.utn.appmovil.containers.model.Container
import cr.ac.utn.appmovil.containers.model.ReleaseRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import util.util

class sof_ListContainer : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var btnRefresh: Button
    private lateinit var btnNew: Button
    private lateinit var adapter: sof_ContainerAdapter
    private var loggedEmail: String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sof_containerlist)

        loggedEmail = intent.getStringExtra("USER_EMAIL") ?: ""

        recycler = findViewById(R.id.sof_recycler_containers)
        btnRefresh = findViewById(R.id.sof_btn_refresh)
        btnNew = findViewById(R.id.sof_btn_new_container)

        adapter = sof_ContainerAdapter(
            onAssignClick = { container -> assignContainer(container.id) },
            onReleaseClick = { container -> releaseContainer(container.id) }
        )

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        btnRefresh.setOnClickListener {
            loadContainers()
        }

        btnNew.setOnClickListener {
            util.openActivity(this, sof_Container::class.java)
        }

        loadContainers()
    }

    override fun onResume() {
        super.onResume()
        loadContainers()
    }

    private fun loadContainers() {
        sof_apiService.sof_ApiService.getContainers()
            .enqueue(object : Callback<ApiResponse<List<Container>>> {

                override fun onResponse(
                    call: Call<ApiResponse<List<Container>>>,
                    response: Response<ApiResponse<List<Container>>>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()

                        if (body != null && body.responseCode == "INFO_FOUND" && body.data != null) {
                            adapter.setData(body.data)
                        } else {
                            Toast.makeText(
                                this@sof_ListContainer,
                                body?.message ?: "No containers found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@sof_ListContainer,
                            "Server error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse<List<Container>>>, t: Throwable) {
                    Toast.makeText(
                        this@sof_ListContainer,
                        "Connection error: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun assignContainer(id: String) {
        val request = AssignRequest(id, loggedEmail)

        sof_apiService.sof_ApiService.assignContainer(request)
            .enqueue(object : Callback<ApiResponse<Container>> {

                override fun onResponse(
                    call: Call<ApiResponse<Container>>,
                    response: Response<ApiResponse<Container>>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()

                        if (body != null && body.responseCode == "INFO_UPDATED") {
                            Toast.makeText(
                                this@sof_ListContainer,
                                body.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            loadContainers()
                        } else {
                            Toast.makeText(
                                this@sof_ListContainer,
                                body?.message ?: "Error assigning container",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@sof_ListContainer,
                            "Server error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Container>>, t: Throwable) {
                    Toast.makeText(
                        this@sof_ListContainer,
                        "Connection error: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun releaseContainer(id: String) {
        val request = ReleaseRequest(id)

        sof_apiService.sof_ApiService.releaseContainer(request)
            .enqueue(object : Callback<ApiResponse<Container>> {

                override fun onResponse(
                    call: Call<ApiResponse<Container>>,
                    response: Response<ApiResponse<Container>>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()

                        if (body != null && body.responseCode == "INFO_UPDATED") {
                            Toast.makeText(
                                this@sof_ListContainer,
                                body.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            loadContainers()
                        } else {
                            Toast.makeText(
                                this@sof_ListContainer,
                                body?.message ?: "Error releasing container",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@sof_ListContainer,
                            "Server error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Container>>, t: Throwable) {
                    Toast.makeText(
                        this@sof_ListContainer,
                        "Connection error: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}
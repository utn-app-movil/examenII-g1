/*package cr.ac.utn.appmovil.containers.mjose_activities

import cr.ac.utn.appmovil.containers.mjose_network.mjose_ApiService
import cr.ac.utn.appmovil.containers.mjose_network.mjose_RetrofitClient
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.appmovil.containers.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import cr.ac.utn.appmovil.containers.mjose_models.mjose_GenericResponse
import cr.ac.utn.appmovil.containers.mjose_models.mjose_Container
import cr.ac.utn.appmovil.containers.mjose_models.mjose_ContainerResponse
import cr.ac.utn.appmovil.containers.mjose_models.mjose_AssignRequest
import cr.ac.utn.appmovil.containers.mjose_models.mjose_ReleaseRequest


class mjose_roomListActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var btnRefresh: Button
    private lateinit var btnNew: Button
    private lateinit var txtError: TextView
    private lateinit var adapter: mjose_ContainerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mjose_room_list)

        recycler = findViewById(R.id.mjose_recyclerContainers)
        btnRefresh = findViewById(R.id.mjose_btnRefresh)
        btnNew = findViewById(R.id.mjose_btnNewContainer)
        txtError = findViewById(R.id.mjose_txtListError)

        adapter = mjose_ContainerAdapter(
            items = emptyList(),
            onAssign = { assignContainer(it) },
            onRelease = { releaseContainer(it) }
        )

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        btnRefresh.setOnClickListener { loadContainers() }

        loadContainers()
    }

    private fun loadContainers() {
        txtError.text = "Loading..."
        val api = mjose_RetrofitClient.instance.create(mjose_ApiService::class.java)

        api.getContainers().enqueue(object : Callback<mjose_ContainerResponse> {
            override fun onResponse(
                call: Call<mjose_ContainerResponse>,
                response: Response<mjose_ContainerResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.responseCode == "INFO_FOUND" && body.data != null) {
                        adapter.updateData(body.data)
                        txtError.text = ""
                    } else {
                        txtError.text = body?.message ?: "No containers found."
                    }
                } else {
                    txtError.text = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<mjose_ContainerResponse>, t: Throwable) {
                txtError.text = "Network error: ${t.localizedMessage}"
            }
        })
    }

    private fun assignContainer(container: mjose_Container) {

        val api = mjose_RetrofitClient.instance.create(mjose_ApiService::class.java)

        val request = mjose_AssignRequest(
            id = container.id,
            technician = "cugalde@gmail.com"   // tu técnico oficial
        )

        api.assignContainer(request).enqueue(object : Callback<mjose_GenericResponse> {

            override fun onResponse(
                call: Call<mjose_GenericResponse>,
                response: Response<mjose_GenericResponse>
            ) {
                Toast.makeText(this@mjose_roomListActivity, "Assigned!", Toast.LENGTH_SHORT).show()
                loadContainers()
            }

            override fun onFailure(
                call: Call<mjose_GenericResponse>,
                t: Throwable
            ) {
                Toast.makeText(this@mjose_roomListActivity, "Error assigning", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun releaseContainer(container: mjose_Container) {
        val api = mjose_RetrofitClient.instance.create(mjose_ApiService::class.java)

        val request = mjose_ReleaseRequest(code = container.code)

        api.releaseContainer(request).enqueue(object : Callback<mjose_GenericResponse> {
            override fun onResponse(
                call: Call<mjose_GenericResponse>,
                response: Response<mjose_GenericResponse>
            ) {
                loadContainers()
            }

            override fun onFailure(call: Call<mjose_GenericResponse>, t: Throwable) {
                Toast.makeText(this@mjose_roomListActivity, "Error releasing", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
*/
package cr.ac.utn.appmovil.containers.mjose_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.appmovil.containers.R
import cr.ac.utn.appmovil.containers.mjose_models.mjose_Container
import cr.ac.utn.appmovil.containers.mjose_models.mjose_ContainerResponse
import cr.ac.utn.appmovil.containers.mjose_models.mjose_AssignRequest
import cr.ac.utn.appmovil.containers.mjose_models.mjose_ReleaseRequest
import cr.ac.utn.appmovil.containers.mjose_models.mjose_GenericResponse
import cr.ac.utn.appmovil.containers.mjose_network.mjose_ApiService
import cr.ac.utn.appmovil.containers.mjose_network.mjose_RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class mjose_roomActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: mjose_ContainerAdapter
    private var containerList = mutableListOf<mjose_Container>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mjose_room)

        recycler = findViewById(R.id.mjose_recycler)
        recycler.layoutManager = LinearLayoutManager(this)

        loadContainers()
    }

    private fun loadContainers() {
        val api = mjose_RetrofitClient.instance.create(mjose_ApiService::class.java)

        api.getContainers().enqueue(object : Callback<mjose_ContainerResponse> {
            override fun onResponse(
                call: Call<mjose_ContainerResponse>,
                response: Response<mjose_ContainerResponse>
            ) {
                val body = response.body()

                if (response.isSuccessful && body != null) {
                    containerList = body.data?.toMutableList() ?: mutableListOf()

                    adapter = mjose_ContainerAdapter(
                        containerList,
                        ::assignContainer,
                        ::releaseContainer
                    )

                    recycler.adapter = adapter

                } else {
                    Toast.makeText(this@mjose_roomActivity, "Error loading containers", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<mjose_ContainerResponse>, t: Throwable) {
                Toast.makeText(this@mjose_roomActivity, "Network error", Toast.LENGTH_SHORT).show()
                Log.e("ROOM", t.message ?: "error")
            }
        })
    }

    private fun assignContainer(container: mjose_Container) {
        val api = mjose_RetrofitClient.instance.create(mjose_ApiService::class.java)

        val request = mjose_AssignRequest(container.id, "maenriquezga@est.utn.ac.cr")

        api.assignContainer(request).enqueue(object : Callback<mjose_GenericResponse> {
            override fun onResponse(
                call: Call<mjose_GenericResponse>,
                response: Response<mjose_GenericResponse>
            ) {
                Toast.makeText(this@mjose_roomActivity, "Assigned!", Toast.LENGTH_SHORT).show()
                loadContainers()
            }

            override fun onFailure(call: Call<mjose_GenericResponse>, t: Throwable) {
                Toast.makeText(this@mjose_roomActivity, "Error assigning", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun releaseContainer(container: mjose_Container) {
        val api = mjose_RetrofitClient.instance.create(mjose_ApiService::class.java)

        val request = mjose_ReleaseRequest(container.id)

        api.releaseContainer(request).enqueue(object : Callback<mjose_GenericResponse> {
            override fun onResponse(
                call: Call<mjose_GenericResponse>,
                response: Response<mjose_GenericResponse>
            ) {
                Toast.makeText(this@mjose_roomActivity, "Released!", Toast.LENGTH_SHORT).show()
                loadContainers()
            }

            override fun onFailure(call: Call<mjose_GenericResponse>, t: Throwable) {
                Toast.makeText(this@mjose_roomActivity, "Error releasing", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

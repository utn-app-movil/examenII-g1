package cr.ac.utn.appmovil.containers.pau_view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cr.ac.utn.appmovil.containers.R
import cr.ac.utn.appmovil.containers.pau_adapter.pau_ContainerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.pau_AssignRequest
import model.pau_ReleaseRequest
import model.pau_Container
import Service.pau_Repository
import android.content.Intent
import util.util
import android.widget.Button

const val PAU_PREFS_NAME = "pau_AppPrefs"
const val PAU_LOGGED_USER_EMAIL_KEY = "pau_loggedUserEmail"

class pau_ContainerActivity : AppCompatActivity() {

    private lateinit var pau_sharedPrefs: SharedPreferences
    private lateinit var pau_adapter: pau_ContainerAdapter
    private val pau_containerList = mutableListOf<pau_Container>()
    private lateinit var pau_loggedEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pau_container)

        pau_sharedPrefs = getSharedPreferences(PAU_PREFS_NAME, Context.MODE_PRIVATE)
        pau_loggedEmail = pau_sharedPrefs.getString(PAU_LOGGED_USER_EMAIL_KEY, "") ?: ""

        val pau_recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.pau_container_recycler_view)
        val pau_createBtn = findViewById<Button>(R.id.pau_createbtn)
        val pau_refreshBtn = findViewById<Button>(R.id.pau_refreshbtn)

        pau_adapter = pau_ContainerAdapter(
            pau_onAssign = { id -> pau_assignContainer(id) },
            pau_onRelease = { id -> pau_releaseContainer(id) },
            pau_loggedEmail = pau_loggedEmail,
            pau_itemList = pau_containerList
        )

        pau_recyclerView.apply {
            layoutManager = LinearLayoutManager(this@pau_ContainerActivity)
            adapter = pau_adapter
        }


        val intent = Intent(this@pau_ContainerActivity, pau_ContainerActivity::class.java)
        startActivity(intent)
        finish()

        pau_refreshBtn.setOnClickListener {
            pau_loadContainers()
        }

        pau_loadContainers()
    }

    private fun pau_loadContainers() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = pau_Repository.pau_getContainers()
                withContext(Dispatchers.Main) {
                    if (response.pau_responseCode == "200") {
                        pau_containerList.clear()
                        response.data?.let { pau_containerList.addAll(it) }
                        pau_adapter.notifyDataSetChanged()
                    } else {
                        util.showDialogCondition(
                            this@pau_ContainerActivity,
                            "Error",
                            response.pau_message,
                            "OK", "Cancelar", {}, {}
                        )
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    util.showDialogCondition(
                        this@pau_ContainerActivity,
                        "Error de red",
                        e.message ?: "No se pudo conectar",
                        "OK", "Cancelar", {}, {}
                    )
                }
            }
        }
    }

    private fun pau_assignContainer(containerId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = pau_Repository.pau_assignContainer(pau_AssignRequest(containerId, pau_loggedEmail))
            withContext(Dispatchers.Main) {
                if (response.pau_responseCode == "200") pau_loadContainers()
                else util.showDialogCondition(this@pau_ContainerActivity, "Error", response.pau_message, "OK", "Cancelar", {}, {})
            }
        }
    }

    private fun pau_releaseContainer(containerId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = pau_Repository.pau_releaseContainer(pau_ReleaseRequest(containerId))
            withContext(Dispatchers.Main) {
                if (response.pau_responseCode == "200") pau_loadContainers()
                else util.showDialogCondition(this@pau_ContainerActivity, "Error", response.pau_message, "OK", "Cancelar", {}, {})
            }
        }
    }

    override fun onResume() {
        super.onResume()
        pau_loadContainers()
}}
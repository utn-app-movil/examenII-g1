package cr.ac.utn.appmovil.containers

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.appmovil.containers.Controller.yadContainerViewModel
import adapter.yadContainerAdapter
import model.yadContainer
import util.yadSessionManager
import com.google.android.material.floatingactionbutton.FloatingActionButton // Para el botón de Refrescar/Crear

class yadContainerListActivity : AppCompatActivity() {

    private lateinit var viewModel: yadContainerViewModel
    private lateinit var containerAdapter: yadContainerAdapter
    private lateinit var sessionManager: yadSessionManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshButton: FloatingActionButton
    private lateinit var createButton: FloatingActionButton // Asumiendo un botón para el punto 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Debes crear este layout: activity_yad_container_list.xml
        setContentView(R.layout.activity_yad_container_list)

        // --- 1. Inicialización ---
        viewModel = ViewModelProvider(this).get(yadContainerViewModel::class.java)
        sessionManager = yadSessionManager(this)

        // --- 2. Configuración del RecyclerView y Adaptador ---
        recyclerView = findViewById(R.id.yad_container_recycler_view)
        refreshButton = findViewById(R.id.yad_button_refresh)
        createButton = findViewById(R.id.yad_button_create_container)

        // Inicializar adaptador con una lista vacía y el Callback de acción
        containerAdapter = yadContainerAdapter(emptyList()) { containerId, actionType ->
            handleContainerAction(containerId, actionType)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@yadContainerListActivity)
            adapter = containerAdapter
        }

        // --- 3. Observación del ViewModel ---

        // Observar la lista de contenedores
        viewModel.containers.observe(this) { response ->
            if (response.responseCode == "INFO_FOUND" && response.data != null) {
                // Actualizar el adaptador con los datos nuevos
                containerAdapter.updateList(response.data as List<yadContainer>)
                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
            } else {
                // Mostrar error si la carga falló
                Toast.makeText(this, "Error al listar: ${response.message}", Toast.LENGTH_LONG).show()
                containerAdapter.updateList(emptyList()) // Limpiar lista
            }
        }

        // Observar el resultado de las acciones (Asignar/Liberar)
        viewModel.actionResult.observe(this) { response ->
            // Mostrar el mensaje de la acción (éxito o error)
            Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
            // El ViewModel recarga la lista automáticamente si la acción fue exitosa.
        }

        // --- 4. Listeners para Botones ---

        // Botón de Refrescar (Punto 3.c)
        refreshButton.setOnClickListener {
            viewModel.loadContainers()
        }

        // Botón de Creación (Punto 2)
        createButton.setOnClickListener {
            val intent = Intent(this, yadContainerCreationActivity::class.java)
            startActivity(intent)
        }

        // Cargar la lista al iniciar la actividad
        viewModel.loadContainers()
    }

    // --- 5. Manejo del Callback del Adaptador ---
    private fun handleContainerAction(containerId: String, actionType: String) {
        val email = sessionManager.getUserEmail()

        if (email == null) {
            Toast.makeText(this, "Error: Sesión no válida. Vuelva a iniciar sesión.", Toast.LENGTH_LONG).show()
            // Opcional: Redirigir a Login
            return
        }

        when (actionType) {
            yadContainerAdapter.ACTION_ASSIGN -> {
                // Asignar contenedor (Punto 3.a)
                viewModel.assignContainer(containerId, email)
            }
            yadContainerAdapter.ACTION_RELEASE -> {
                // Liberar contenedor (Punto 3.b)
                viewModel.releaseContainer(containerId)
            }
        }
    }
}
package cr.ac.utn.appmovil.containers

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import cr.ac.utn.appmovil.containers.Controller.yadContainerViewModel
import model.yadContainer

class yadContainerCreationActivity : AppCompatActivity() {

    private lateinit var viewModel: yadContainerViewModel
    private lateinit var nameInput: EditText
    private lateinit var locationInput: EditText
    private lateinit var createButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yad_container_creation)

        viewModel = ViewModelProvider(this).get(yadContainerViewModel::class.java)

        nameInput = findViewById(R.id.yad_input_container_name)
        locationInput = findViewById(R.id.yad_input_container_location)
        createButton = findViewById(R.id.yad_button_create)

        // 2. Listener del botón de creación
        createButton.setOnClickListener {
            attemptCreateContainer()
        }

        viewModel.actionResult.observe(this) { response ->
            Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()

            // Si la creación es exitosa, se puede cerrar la actividad
            if (response.responseCode == "ACTION_SUCCESSFUL") {
                finish()
            }
        }
    }

    private fun attemptCreateContainer() {
        val name = nameInput.text.toString()
        val location = locationInput.text.toString()

        if (name.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Debe completar el nombre y la ubicación.", Toast.LENGTH_SHORT).show()
            return
        }

        val newContainer = yadContainer(
            containerId = "", // El ID se generará en el servidor
            name = name,
            location = location,
            assignedTo = null // No asignado al crearse
        )

        viewModel.createContainer(newContainer)
    }
}